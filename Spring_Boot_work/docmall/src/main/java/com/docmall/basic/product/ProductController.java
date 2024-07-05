package com.docmall.basic.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.basic.admin.product.ProductVo;
import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.common.dto.PageDTO;
import com.docmall.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	
	private final ProductService productService;
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//CKeditor 파일업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	
	@GetMapping("/pro_list")
	public void pro_list(@ModelAttribute("cat_code")int cat_code, @ModelAttribute("cat_name") String cat_name, Criteria cri, Model model) throws Exception {
		
		log.info("2차 카테고리코드 " + cat_code);
		log.info("2차 카테고리이름 " + cat_name);
		
		cri.setAmount(9);
		
		List<ProductVo> pro_list = productService.pro_list(cat_code, cri);
		// 클라이언트에 \를 /로 변환하여 model작업 전에 처리
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")); // 역슬래시 하나만 쓰면 에러맞음.
		});
		
		int totalCount = productService.getCountProductByCategory(cat_code);
		
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품리스트에서 사용할 이미지 보여주기. 1) <img src="매핑주소"> 2) <img src="test.gif"> 우리는 1) 사용.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 상품정보 1
	@GetMapping("/pro_info")
	public ResponseEntity<ProductVo> pro_info(int pro_num) throws Exception {
		ResponseEntity<ProductVo> entity = null;
		
		// db연동
		// ajax로 그대로 넘어가는건 model 작업 안함. jsp, thymeleaf에서 보여주기 위해선 또 model작업하고
		// (리턴타입, ok)
		ProductVo vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		entity = new ResponseEntity<ProductVo>(vo, HttpStatus.OK);
		
		return entity;
	}
	
	// 상품정보 2
	@GetMapping("/pro_info_2")
	public void pro_info_2(int pro_num, Model model) throws Exception {
		log.info("상품코드" + pro_num);
		
		// db 연동
		ProductVo vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("product", vo);
	}
	
	
	
	
	
}
