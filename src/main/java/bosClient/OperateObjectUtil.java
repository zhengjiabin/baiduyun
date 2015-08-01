package bosClient;

import java.net.URL;

import javax.ws.rs.core.MediaType;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.CopyObjectRequest;
import com.baidubce.services.bos.model.CopyObjectResponse;
import com.baidubce.services.bos.model.ListObjectsRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;

/**
 * 操作Object工具
 * 
 * @author Administrator
 * 
 */
public class OperateObjectUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * <pre>
     * 查看bucket中的Object列表
     * 1、默认情况下，如果Bucket中的Object数量大于1000，则只会返回1000个Object，并且返回结果中IsTruncated值为True，并返回NextMarker做为下次读取的起点
     * 2、若想增大返回Object的数目，可以使用Marker参数分次读取
     * </pre>
     */
    public static void listObjects(String bucketName) {
        // 获取指定Bucket下的所有Object信息
        ListObjectsResponse listing = bosClient.listObjects(bucketName);
        
        // 遍历所有Object
        for (BosObjectSummary objectSummary : listing.getContents()) {
            System.out.println("ObjectKey: " + objectSummary.getKey());
        }
    }
    
    /**
     * 查看bucket中的Object列表(高级)
     */
    public static void listObjectsAdvanced(String bucketName) {
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        
        // 设置参数
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMarker("123");
        listObjectsRequest.setPrefix("xx");
        listObjectsRequest.setMaxKeys(1000);
        
        ListObjectsResponse listing = bosClient.listObjects(listObjectsRequest);
        
        // 遍历所有Object
        for (BosObjectSummary objectSummary : listing.getContents()) {
            System.out.println("ObjectKey: " + objectSummary.getKey());
        }
    }
    
    /**
     * 获取下载Object的URL
     * 
     * @return
     */
    public static String generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
        URL url = bosClient.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        
        return url.toString();
    }
    
    /**
     * 删除Object
     */
    public static void deleteObject(String bucketName, String objectKey) {
        bosClient.deleteObject(bucketName, objectKey);
    }
    
    /**
     * 拷贝Object
     */
    public static void copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // 拷贝Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        // 打印结果
        System.out.println("ETag: " + copyObjectResponse.getETag() + " LastModified: " + copyObjectResponse.getLastModified());
    }
    
    /**
     * 拷贝Object(高级)
     */
    public static void copyObjectAdvanced(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // 创建CopyObjectRequest对象
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketName, srcKey, destBucketName, destKey);
        
        // 设置新的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        // 设置ContentLength大小
        metadata.setContentLength(1000);
        // 设置自定义元数据name的值为my-data
        metadata.addUserMetadata("name", "my-data");
        // 设置ContentType
        metadata.setContentType(MediaType.APPLICATION_JSON);
        
        copyObjectRequest.setNewObjectMetadata(metadata);
        
        // 复制Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(copyObjectRequest);
        
        System.out.println("ETag: " + copyObjectResponse.getETag() + " LastModified: " + copyObjectResponse.getLastModified());
    }
}
