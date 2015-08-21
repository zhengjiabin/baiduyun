package bosClient;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.Grantee;
import com.baidubce.services.bos.model.Permission;

/**
 * 测试bucket
 * 
 * @author Administrator
 * 
 */
public class TestBucket {
	/**
	 * 创建bucket
	 */
	@Test
	public void createBucket() {
		String bucketName = "binbinpictures";
		BosBucketUtil.createBucket(bucketName);
	}

	/**
	 * 遍历bucket
	 */
	@Test
	public void getBuckets() {
		BosBucketUtil.getBuckets();
	}

	/**
	 * 判断bucket是否存在
	 */
	@Test
	public void doesBucketExist() {
		String bucketName = "binbinpictures";
		BosBucketUtil.doesBucketExist(bucketName);
	}

	/**
	 * 删除bucket
	 * 
	 * @param client
	 * @param bucketName
	 */
	@Test
	public void deleteBucket() {
		String bucketName = "photo";
		BosBucketUtil.deleteBucket(bucketName);
	}

	/**
	 * 设置bucket访问权限
	 * 
	 * @param client
	 * @param bucketName
	 */
	@Test
	public void setBucketPrivate() {
		String bucketName = "test";
		BosBucketUtil.setBucketPrivate(bucketName, CannedAccessControlList.Private);
	}

	/**
	 * 指定用户的访问权限
	 * 
	 * @param client
	 */
	@Test
	public void SetBucketAclFromBody() {
		String bucketName = "";

		List<Grant> grants = new ArrayList<Grant>();
		List<Grantee> grantees = new ArrayList<Grantee>();
		List<Permission> permissiones = new ArrayList<Permission>();

		// 授权给特定用户
		Grantee grantee = new Grantee("userId");
		grantees.add(grantee);

		// 授权给Everyone
		Grantee grantee_all = new Grantee("*");
		grantees.add(grantee_all);

		// 设置权限
		permissiones.add(Permission.READ);
		permissiones.add(Permission.WRITE);

		Grant grant = new Grant();
		grant = grant.withGrantee(grantees);
		grant = grant.withPermission(permissiones);
		grants.add(grant);

		BosBucketUtil.SetBucketAclFromBody(bucketName, grants);
	}
}
