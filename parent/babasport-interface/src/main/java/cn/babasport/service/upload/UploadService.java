package cn.babasport.service.upload;

public interface UploadService {
		//附件上传
		public String uploadPic(byte[] file_buff,String filename) throws Exception;
}
