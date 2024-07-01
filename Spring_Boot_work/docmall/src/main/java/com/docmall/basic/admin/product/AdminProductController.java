package com.docmall.basic.admin.product;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.basic.admin.category.AdminCategoryService;
import com.docmall.basic.admin.category.AdminCategoryVo;
import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.common.dto.PageDTO;
import com.docmall.basic.common.util.FileManagerUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/*")
@Slf4j
public class AdminProductController {

	private final AdminProductService adminProductService;
	private final AdminCategoryService adminCategoryService;
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//CKeditor 파일업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	
	// 상품등록폼
	@GetMapping("pro_insert")
	public void pro_insertForm(Model model) {
		
		List<AdminCategoryVo> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", cate_list);
	}
	
	@PostMapping("pro_insert")
	// name="uploadFile" 이름을 파라미터명으로 사용함.
	public String pro_insertOk(ProductVo vo, MultipartFile uploadFile) throws Exception {
		
		// 1) 상품이미지 업로드
		String dateFoler = FileManagerUtils.getDataFolder();
		String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFoler, uploadFile);
		
		vo.setPro_img(saveFileName);
		vo.setPro_up_folder(dateFoler);
		
		log.info("상품정보 : " + vo);
		
		// 2) 상품정보 DB저장
		adminProductService.pro_insert(vo);
		
		return "redirect:/admin/product/pro_list";
	}
	
	// CKEditor 상품설명 이미지 업로드
	// MultipartFile upload : CKeditor의 업로드탭에서 나온 파일첨부 <input type="file" name="upload"> 을 참조함.
	// HttpServletRequest request : 클라이언트의 요청정보를 가지고 있는 객체.
	// HttpServletResponse response : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	@PostMapping("/imageupload")
	// name이 upload로 되어 있음.
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		
		OutputStream out = null;
		PrintWriter printWriter = null; // 서버에서 클라이언트에게 응답정보를 보낼때 사용.(업로드한 이미지정보를 브라우저에게 보내는 작업용도)
		
		try {
			//1)CKeditor를 이용한 파일업로드 처리.
			String fileName = upload.getOriginalFilename(); // 업로드 할 클라이언트 파일이름
			byte[] bytes = upload.getBytes(); // 업로드 할 파일의 바이트배열
			
			String ckUploadPath = uploadCKPath + fileName; // "C:\\Dev\\upload\\ckeditor\\" + "abc.gif"
			
			out = new FileOutputStream(ckUploadPath); // "C:\\Dev\\upload\\ckeditor\\abc.gif" 파일생성. 0 byte
			
			out.write(bytes); // 빨대(스트림)의 공간에 업로드할 파일의 바이트배열을 채운상태.
			out.flush(); // "C:\\Dev\\upload\\ckeditor\\abc.gif" 0 byte -> 크기가 채워진 정상적인 파일로 인식.
			
			//2)업로드한 이미지파일에 대한 정보를 클라이언트에게 보내는 작업
			
			printWriter = response.getWriter();
			
			
			String fileUrl = "/admin/product/display/" + fileName; // 매핑주소/이미지파일명
//				String fileUrl = fileName;
			
			
			// Ckeditor 4.12에서는 파일정보를 다음과 같이 구성하여 보내야 한다.
			// {"filename" : "abc.gif", "uploaded":1, "url":"/ckupload/abc.gif"}
			// {"filename" : 변수, "uploaded":1, "url":변수}
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 채움.
			printWriter.flush();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) printWriter.close();
		}
	}
	
	@GetMapping("/display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		
		log.info("파일이미지: " + fileName);
		
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileManagerUtils.getFile(uploadCKPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// 상품리스트
	@GetMapping("/pro_list")
	public void pro_list(Criteria cri, Model model) throws Exception {
		
//		cri.setAmount(2); // Criteria를 직접 바꾸지 않고 페이징 기능 제대로 되는지 확인.
		log.info("Criteria : " + cri);
		
		List<ProductVo> pro_list = adminProductService.pro_list(cri);
		// 클라이언트에 \를 /로 변환하여 model작업 전에 처리
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "//")); // 역슬래시 하나만 쓰면 에러맞음.
		});
		
		int totalCount = adminProductService.getTotalCount(cri);
		
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품리스트에서 사용할 이미지 보여주기. 1) <img src="매핑주소"> 2) <img src="test.gif"> 우리는 1) 사용.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 상품수정 폼
	@GetMapping("/pro_edit") // Criteria정보를 숨겨두어야 해서 @ModelAttribute 사용
	public void pro_edit(@ModelAttribute("cri") Criteria cri, Integer pro_num, Model model) throws Exception {
	
		// 1차 카테고리 목록
		List<AdminCategoryVo> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", cate_list);
		
		// 상품정보(2차 카테고리)
		// model이름 : productVo
		ProductVo vo = adminProductService.pro_edit(pro_num);
		// 클라이언트에 \를 /로 변환하여 model작업 전에 처리 예> 2024\07\01 -> 2024/07/01
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "//"));
		model.addAttribute("productVo", vo);
		
		// 1차 카테고리
		int cat_code = vo.getCat_code(); // 상품테이블에 존재하는 2차 카테고리 코드
		int cat_prtcode = adminCategoryService.getFirstCategoryBySecondCategory(cat_code).getCat_prtcode();
		// model.addAttribute("categoryVo", adminCategoryService.getFirstCategoryBySecondCategory(cat_code));
		model.addAttribute("cat_prtcode", cat_prtcode);
		
		// 2차 카테고리 목록
		model.addAttribute("sub_cate_list", adminCategoryService.getSecondCategoryList(cat_prtcode));
	}
	
	// 상품수정하기
	@PostMapping("pro_edit")
	public String pro_edit(ProductVo vo, MultipartFile uploadFile, Criteria cri, RedirectAttributes rttr) throws Exception {
		
		log.info("상품수정정보 : " + vo);
		// 상품이미지 변경(업로드) 유무
		if(!uploadFile.isEmpty()) {
			// 기존 상품이미지 삭제. 날짜폴더명, 파일명
			FileManagerUtils.delete(uploadPath, vo.getPro_up_folder(), vo.getPro_img(), "image");
			
			// 변경 이미지 업로드
			String dateFoler = FileManagerUtils.getDataFolder();
			String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFoler, uploadFile);
			
			// 새로운 이미지 파일명, 날짜폴더명
			vo.setPro_img(saveFileName);
			vo.setPro_up_folder(dateFoler);
		}
		
		// DB저장
		adminProductService.pro_edit_ok(vo);
		
		return "redirect:/admin/product/pro_list" + cri.getListLink();
	}
	
	
	
	
}
