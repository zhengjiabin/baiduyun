package bosClient;

import java.io.IOException;

import org.junit.Test;

/**
 * 测试上传Object
 * 
 * @author Administrator
 * 
 */
public class TestUpload {
	/**
	 * <pre>
	 * 测试文件上传
	 * 1、putObject函数支持不超过5GB的Object上传
	 * 2、BOS会在Header中返回Object的ETag作为文件标识
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void putObject() throws IOException {
		String bucketName = "binbinpictures";
		String newFileName = "photo/test";
		String filePath = "E:/工作文档/酒业文档/temp/tupian.png";

		String eTag = BosUploadUtil.putObject(bucketName, newFileName, filePath);
		System.out.println(eTag);

		String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, filePath);
		System.out.println(directory);
	}

	/**
	 * 测试文件上传
	 */
	@Test
	public void putObjectAdvanced() {
		String bucketName = "binbinpictures/pictures";
		String newFileName = "test";
		String filePath = "E:/工作文档/酒业文档/temp/tupian.png";

		String eTag = BosUploadUtil.putObjectAdvanced(bucketName, newFileName, filePath);
		System.out.println(eTag);
	}
}
