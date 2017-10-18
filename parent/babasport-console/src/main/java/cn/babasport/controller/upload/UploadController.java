package cn.babasport.controller.upload;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import cn.babasport.service.upload.UploadService;
import cn.babasport.utils.constants.BbsConstants;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@Resource
	private UploadService uploadService;

	// 品牌管理附件上传
	@RequestMapping("/uploadPic.do")
	public void uploadPic(MultipartFile pic, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 判断接收到的附件
		if (pic != null && pic.getSize() > 0) {
			// 将附件上传到FastDFS上去
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename());
			String allUrl= BbsConstants.IMG_URL+path;//回显图片的路径
			
			
			//响应结果
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("allUrl", allUrl);//jsp页面回显
			jsonObject.put("imgUrl", path);//保存到数据库
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObject.toString());

		}
	}

	/*
	 * @RequestMapping("/uploadPic.do") public void uploadPic(MultipartFile pic,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception{ // 判断接收到的附件 if(pic != null && pic.getSize() > 0){ // 1、修改附件名称
	 * String filename = pic.getOriginalFilename(); // 例如：1.jpg jpg String
	 * suffix = FilenameUtils.getExtension(filename); String uuid =
	 * UUID.randomUUID().toString().replace("-", ""); String newName = uuid +
	 * "." + suffix; // 2、附件上传 保存到项目的真实路径下 String realPath =
	 * request.getServletContext().getRealPath(""); String allUrl =
	 * "\\upload\\"+newName; String path = realPath + allUrl; File file = new
	 * File(path); pic.transferTo(file); // 3、响应结果 JSONObject jsonObject = new
	 * JSONObject(); jsonObject.put("allUrl", allUrl);
	 * response.setContentType("application/json;charset=UTF-8");
	 * response.getWriter().write(jsonObject.toString()); } }
	 */

}
