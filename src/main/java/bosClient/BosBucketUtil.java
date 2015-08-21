package bosClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.ListBucketsResponse;
import com.baidubce.services.bos.model.SetBucketAclRequest;

/**
 * bucket操作工具
 * 
 * @author Administrator
 * 
 */
public class BosBucketUtil {
	private static BosClient bosClient = BosClientUtil.getInstance();

	/**
	 * 创建bucket
	 */
	public static void createBucket(String bucketName) {
		bosClient.createBucket(bucketName);
	}

	/**
	 * 获取所有bucket
	 */
	public static List<BucketSummary> getBuckets() {
		ListBucketsResponse listBucketsResponse = bosClient.listBuckets();
		if (listBucketsResponse == null) {
			return new ArrayList<BucketSummary>();
		}

		List<BucketSummary> buckets = listBucketsResponse.getBuckets();
		return buckets;
	}

	/**
	 * 判断bucket是否存在
	 */
	public static boolean doesBucketExist(String bucketName) {
		if (StringUtils.isEmpty(bucketName)) {
			return false;
		}

		boolean exists = bosClient.doesBucketExist(bucketName);
		return exists;
	}

	/**
	 * 删除bucket
	 * 
	 * @param client
	 * @param bucketName
	 */
	public static void deleteBucket(String bucketName) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		bosClient.deleteBucket(bucketName);
	}

	/**
	 * 设置bucket访问权限
	 * 
	 * @param client
	 * @param bucketName
	 */
	public static void setBucketPrivate(String bucketName, CannedAccessControlList acl) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		bosClient.setBucketAcl(bucketName, acl);
	}

	/**
	 * 指定用户的访问权限
	 * 
	 * @param client
	 */
	public static void SetBucketAclFromBody(String bucketName, List<Grant> grants) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		// List<Grant> grants = new ArrayList<Grant>();
		// List<Grantee> grantees = new ArrayList<Grantee>();
		// List<Permission> permissiones = new ArrayList<Permission>();
		//
		// // 授权给特定用户
		// Grantee grantee = new Grantee("userId");
		// grantees.add(grantee);
		//
		// // 授权给Everyone
		// Grantee grantee_all = new Grantee("*");
		// grantees.add(grantee_all);
		//
		// // 设置权限
		// permissiones.add(Permission.READ);
		// permissiones.add(Permission.WRITE);
		//
		// Grant grant = new Grant();
		// grant = grant.withGrantee(grantees);
		// grant = grant.withPermission(permissiones);
		// grants.add(grant);

		SetBucketAclRequest bucketAclRequest = new SetBucketAclRequest(bucketName, grants);
		bosClient.setBucketAcl(bucketAclRequest);
	}
}
