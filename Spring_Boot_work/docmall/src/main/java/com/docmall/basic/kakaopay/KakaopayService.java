package com.docmall.basic.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by kakaopay
 */
@Service
@Slf4j
public class KakaopayService {
	@Value("${kakaopay.api.secret.key}")
    private String kakaopaySecretKey;

    @Value("${cid}")
    private String cid;
    
    @Value("${approval}")
    private String approval;
    
    @Value("${cancel}")
    private String cancel;
    
    @Value("${fail}")
    private String fail;

//    @Value("${sample.host}")
//    private String sampleHost;

    private String tid;
    private String partnerOrderId; // approve에서 사용되기 때문에 전역변수로 뺌.
    private String partnerUserId; // approve에서 사용되기 때문에 전역변수로 뺌.

    // 1) 결제 준비 요청(ready)
    public ReadyResponse ready(String partnerOrderId, String partnerUserId, String itemName,
    		int quantity, int totalAmount, int taxFreeAmount, int vatAmount) {
    	log.info("레디 입장");
        // Request header
        HttpHeaders headers = new HttpHeaders(); // header에 담아달라고 하여 이렇게 사용.
        headers.add("Authorization", "SECRET_KEY " + kakaopaySecretKey);
        headers.set("Content-type", "application/json;charset=utf-8");

        // Request param
        // 객체 지향 언어에는 builder패턴이란 것이 있음.
        ReadyRequest readyRequest = ReadyRequest.builder()
                .cid(cid)
                .partnerOrderId(partnerOrderId) // partnerOrderId와 partnerUserId가 다르면 에러.
                .partnerUserId(partnerUserId)
                .itemName(itemName)
                .quantity(quantity)
                .totalAmount(totalAmount)
                .taxFreeAmount(taxFreeAmount)
                .vatAmount(vatAmount)
                .approvalUrl(approval) // 성공. 카카오페이 서버에서 이 주소를 찾아옴.
                .cancelUrl(cancel) // 취소
                .failUrl(fail) // 실패
                .build();

        // Send reqeust //RestTemplate 은 요청 시 사용하는 class
        HttpEntity<ReadyRequest> entityMap = new HttpEntity<>(readyRequest, headers);
        ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                entityMap,
                ReadyResponse.class
        );
        ReadyResponse readyResponse = response.getBody();
        log.info("ready메서드 내 응답데이터 : " + readyResponse);

        // 주문번호와 TID를 매핑해서 저장해놓는다.
        // Mapping TID with partner_order_id then save it to use for approval request.
        this.tid = readyResponse.getTid(); // 전역변수로 작업
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        
        return readyResponse;
    }

    // 2) 결제 승인 요청(approve)
    public String approve(String pgToken) {
        // ready할 때 저장해놓은 TID로 승인 요청
        // Call “Execute approved payment” API by pg_token, TID mapping to the current payment transaction and other parameters.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaopaySecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request param
        ApproveRequest approveRequest = ApproveRequest.builder()
                .cid(cid)
                .tid(tid)
                .partnerOrderId(partnerOrderId)
                .partnerUserId(partnerUserId)
                .pgToken(pgToken)
                .build();

        // Send Request
        HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
        try {
            ResponseEntity<String> response = new RestTemplate().postForEntity(
                    "https://open-api.kakaopay.com/online/v1/payment/approve",
                    entityMap,
                    String.class
            );

            // 승인 결과를 저장한다.
            // save the result of approval
            String approveResponse = response.getBody();
            return approveResponse;
        } catch (HttpStatusCodeException ex) {
            return ex.getResponseBodyAsString();
        }
    }
}
