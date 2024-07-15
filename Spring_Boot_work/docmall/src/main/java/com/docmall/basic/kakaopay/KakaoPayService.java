package com.docmall.basic.kakaopay;

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
public class KakaoPayService {
	/*
	@Value("${kakaopay.api.secret.key}")
    private String kakaopaySecretKey;

    @Value("${cid}")
    private String cid;

    @Value("${sample.host}")
    private String sampleHost;
    */

    private String tid;

    // 1) 결제 준비 요청(ready)
    public ReadyResponse ready() {
    	log.info("레디 입장");
        // Request header
        HttpHeaders headers = new HttpHeaders(); // header에 담아달라고 하여 이렇게 사용.
        headers.add("Authorization", "SECRET_KEY DEV3B4119920DC9350EF6F52BF60ADA52E9C77D6");
        headers.set("Content-type", "application/json;charset=utf-8");

        // Request param
        // 객체 지향 언어에는 builder패턴이란 것이 있음.
        ReadyRequest readyRequest = ReadyRequest.builder()
                .cid("TC0ONETIME")
                .partnerOrderId("1") // partnerOrderId와 partnerUserId가 다르면 에러.
                .partnerUserId("1")
                .itemName("상품명")
                .quantity(1)
                .totalAmount(1100)
                .taxFreeAmount(0)
                .vatAmount(100)
                .approvalUrl("http://localhost:9095/kakao/approval") // 성공. 카카오페이 서버에서 이 주소를 찾아옴.
                .cancelUrl("http://localhost:9095/kakao/cancel") // 취소
                .failUrl("http://localhost:9095/kakao/fail") // 실패
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
        
        return readyResponse;
    }

    // 2) 결제 승인 요청(approve)
    public String approve(String pgToken) {
        // ready할 때 저장해놓은 TID로 승인 요청
        // Call “Execute approved payment” API by pg_token, TID mapping to the current payment transaction and other parameters.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY DEV3B4119920DC9350EF6F52BF60ADA52E9C77D6");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request param
        ApproveRequest approveRequest = ApproveRequest.builder()
                .cid("TC0ONETIME")
                .tid(tid)
                .partnerOrderId("1")
                .partnerUserId("1")
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
