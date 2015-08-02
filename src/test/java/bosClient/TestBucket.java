package bosClient;

import org.junit.Test;

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
        BucketUtil.createBucket(bucketName);
    }
    
    /**
     * 遍历bucket
     */
    @Test
    public void getBuckets() {
        BucketUtil.getBuckets();
    }
    
    /**
     * 判断bucket是否存在
     */
    @Test
    public void doesBucketExist() {
        String bucketName = "binbinpictures";
        BucketUtil.doesBucketExist(bucketName);
    }
    
    /**
     * 删除bucket
     * 
     * @param client
     * @param bucketName
     */
    @Test
    public void deleteBucket() {
        String bucketName = "binbinpictures";
        BucketUtil.deleteBucket(bucketName);
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
        BucketUtil.setBucketPrivate(bucketName);
    }
    
    /**
     * 指定用户的访问权限
     * 
     * @param client
     */
    @Test
    public void SetBucketAclFromBody() {
        String bucketName = "";
        BucketUtil.SetBucketAclFromBody(bucketName);
    }
}
