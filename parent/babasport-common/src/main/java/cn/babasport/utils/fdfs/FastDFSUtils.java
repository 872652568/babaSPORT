package cn.babasport.utils.fdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 * 说明：FastDFS工具类
 * @author 大富豪.嘉祥先生
 * @version 1.0
 * @date 2017年10月19日
 */
 
public class FastDFSUtils {

	public static String uploadPicToFastDFS(byte[] file_buff, String filename) throws Exception{
		// 1、加载FastDFS配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		// 2、初始化FastDFS配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		// 3、创建跟踪服务器客户端
		TrackerClient trackerClient = new TrackerClient();
		// 4、通过跟踪服务器客户端获取到服务端
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、通过跟踪服务器创建存储器客户端
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		// 6、附件上传
		String file_ext_name = FilenameUtils.getExtension(filename);
		// 注意：返回路径中不包含服务器地址    例如：group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg  
		String path = storageClient1.upload_file1(file_buff, file_ext_name, null);
		return path;
	}
}
