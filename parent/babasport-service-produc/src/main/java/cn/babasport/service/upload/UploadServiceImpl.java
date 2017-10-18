package cn.babasport.service.upload;

import org.springframework.stereotype.Service;

import cn.babasport.utils.fdfs.FastDFSUtils;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadPic(byte[] file_buff, String filename) throws Exception {
		String path = FastDFSUtils.uploadPicToFastDFS(file_buff, filename);
		return path;
	}

}
