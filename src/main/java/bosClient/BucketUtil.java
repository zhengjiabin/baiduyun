package bosClient;

import java.util.ArrayList;
import java.util.List;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.Grantee;
import com.baidubce.services.bos.model.Permission;
import com.baidubce.services.bos.model.SetBucketAclRequest;

/**
 * bucket操作工具
 * 
 * @author Administrator
 * 
 */
public class BucketUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * 创建bucket
     */
    public static void createBucket(String bucketName) {
        bosClient.createBucket(bucketName);
    }
    
    /**
     * 遍历bucket
     */
    public static void getBuckets() {
        // 获取用户的Bucket列表
        List<BucketSummary> buckets = bosClient.listBuckets().getBuckets();
        
        // 遍历Bucket
        for (BucketSummary bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }
    
    /**
     * 判断bucket是否存在
     */
    public static boolean doesBucketExist(String bucketName) {
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
        bosClient.deleteBucket(bucketName);
    }
    
    /**
     * 设置bucket访问权限
     * 
     * @param client
     * @param bucketName
     */
    public static void setBucketPrivate(String bucketName) {
        bosClient.setBucketAcl(bucketName, CannedAccessControlList.Private);
    }
    
    /**
     * 指定用户的访问权限
     * 
     * @param client
     */
    public static void SetBucketAclFromBody(String bucketName) {
        List<Grant> grants = new ArrayList<Grant>();
        List<Grantee> grantees = new ArrayList<Grantee>();
        List<Permission> permissiones = new ArrayList<Permission>();
        
        //授权给特定用户
        Grantee grantee = new Grantee("userId");
        grantees.add(grantee);
        
        //授权给Everyone
        Grantee grantee_all = new Grantee("*");
        grantees.add(grantee_all);
        
        //设置权限
        permissiones.add(Permission.READ);
        permissiones.add(Permission.WRITE);
        
        Grant grant = new Grant();
        grant = grant.withGrantee(grantees);
        grant = grant.withPermission(permissiones);
        grants.add(grant);
        
        SetBucketAclRequest bucketAclRequest = new SetBucketAclRequest(bucketName, grants);
        bosClient.setBucketAcl(bucketAclRequest);
    }
}
