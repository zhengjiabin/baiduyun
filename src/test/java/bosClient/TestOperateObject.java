package bosClient;

import java.util.List;

import org.junit.Test;

import com.baidubce.services.bos.model.BosObjectSummary;

/**
 * 操作Object(包括拷贝、删除、查询、遍历、查看Object的URL)
 * 
 * @author Administrator
 * 
 */
public class TestOperateObject {
	/**
	 * <pre>
	 * 查看bucket中的Object列表
	 * 1、默认情况下，如果Bucket中的Object数量大于1000，则只会返回1000个Object，并且返回结果中IsTruncated值为True，并返回NextMarker做为下次读取的起点
	 * 2、若想增大返回Object的数目，可以使用Marker参数分次读取
	 * </pre>
	 */
	@Test
	public void listObjects() {
		String bucketName = "binbinpictures";

		List<BosObjectSummary> listObjects = OperateObjectUtil.listObjects(bucketName);
		for (BosObjectSummary bosObjectSummary : listObjects) {
			System.out.println(bosObjectSummary.getETag());
		}
	}

	/**
	 * 查看bucket中的Object列表(高级)
	 */
	@Test
	public void listObjectsAdvanced() {
		// 构造ListObjectsRequest请求
		String bucketName = "binbinpictures";

		List<BosObjectSummary> listObjectsAdvanced = OperateObjectUtil.listObjectsAdvanced(bucketName);
		for (BosObjectSummary bosObjectSummary : listObjectsAdvanced) {
			System.out.println(bosObjectSummary.getETag());
		}
	}

	/**
	 * 获取下载Object的URL
	 * 
	 * @return
	 */
	@Test
	public void generatePresignedUrl() {
		String bucketName = "binbinpictures";
		String objectKey = "test";

		String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, objectKey);
		System.out.println(directory);
	}

	/**
	 * 删除Object
	 */
	@Test
	public void deleteObject() {
		String bucketName = "binbinpictures";
		String objectKey = "xxxx";

		OperateObjectUtil.deleteObject(bucketName, objectKey);
	}

	/**
	 * 拷贝Object
	 */
	@Test
	public void copyObject() {
		String srcBucketName = "";
		String srcKey = "";

		String destBucketName = "";
		String destKey = "";

		OperateObjectUtil.copyObject(srcBucketName, srcKey, destBucketName, destKey);
	}

	/**
	 * 拷贝Object(高级)
	 */
	@Test
	public void copyObjectAdvanced() {
		String srcBucketName = "";
		String srcKey = "";

		String destBucketName = "";
		String destKey = "";

		OperateObjectUtil.copyObjectAdvanced(srcBucketName, srcKey, destBucketName, destKey);
	}
}
