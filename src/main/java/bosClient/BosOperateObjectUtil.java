package bosClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import org.springframework.util.StringUtils;

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
public class BosOperateObjectUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    private static Properties prop;
    
    static {
        prop = new Properties();
        try {
            prop.load(BosOperateObjectUtil.class.getResourceAsStream("/baiduBos.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <pre>
     * 查看bucket中的Object列表
     * 1、默认情况下，如果Bucket中的Object数量大于1000，则只会返回1000个Object，并且返回结果中IsTruncated值为True，并返回NextMarker做为下次读取的起点
     * 2、若想增大返回Object的数目，可以使用Marker参数分次读取
     * </pre>
     */
    public static List<BosObjectSummary> listObjects(String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return new ArrayList<BosObjectSummary>();
        }
        
        // 获取指定Bucket下的所有Object信息
        ListObjectsResponse listObjectsResponse = bosClient.listObjects(bucketName);
        if (listObjectsResponse == null) {
            return new ArrayList<BosObjectSummary>();
        }
        
        List<BosObjectSummary> contents = listObjectsResponse.getContents();
        return contents;
    }
    
    /**
     * 查看bucket中的Object列表(高级)
     */
    public static List<BosObjectSummary> listObjectsAdvanced(String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return new ArrayList<BosObjectSummary>();
        }
        
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        
        // 设置参数
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMarker("123");
        listObjectsRequest.setPrefix("xx");
        listObjectsRequest.setMaxKeys(1000);
        
        ListObjectsResponse listObjectsResponse = bosClient.listObjects(listObjectsRequest);
        if (listObjectsResponse == null) {
            return new ArrayList<BosObjectSummary>();
        }
        
        List<BosObjectSummary> contents = listObjectsResponse.getContents();
        return contents;
    }
    
    /**
     * 获取下载Object的URL
     * 
     * @param bucketName
     * @param objectKey
     * @param expirationInSeconds
     * @return
     */
    public static URL generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(objectKey)) {
            return null;
        }
        
        URL url = bosClient.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        return url;
    }
    
    /**
     * 获取下载Object的URL
     * 
     * @return
     */
    public static String generatePresignedUrlDirectory(String bucketName, String objectKey, int expirationInSeconds) {
        URL url = generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        if (url == null) {
            return null;
        }
        
        return url.toString();
    }
    
    /**
     * 获取下载Object的URL
     * 
     * @return
     */
    public static String generatePresignedUrlDirectory(String bucketName, String objectKey) {
        int expirationInSeconds = Integer.parseInt(prop.getProperty("expirationInSeconds"));
        URL url = generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        if (url == null) {
            return null;
        }
        
        return url.toString();
    }
    
    /**
     * 删除Object
     */
    public static void deleteObject(String bucketName, String objectKey) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(objectKey)) {
            return;
        }
        
        try {
            bosClient.deleteObject(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 拷贝Object
     */
    public static String copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        if (StringUtils.isEmpty(srcBucketName) || StringUtils.isEmpty(srcKey) || StringUtils.isEmpty(destBucketName) || StringUtils.isEmpty(destKey)) {
            return null;
        }
        
        // 拷贝Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        if (copyObjectResponse == null) {
            return null;
        }
        
        String eTag = copyObjectResponse.getETag();
        
        return eTag;
    }
    
    /**
     * 拷贝Object(高级)
     */
    public static String copyObjectAdvanced(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        if (StringUtils.isEmpty(srcBucketName) || StringUtils.isEmpty(srcKey) || StringUtils.isEmpty(destBucketName) || StringUtils.isEmpty(destKey)) {
            return null;
        }
        
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
        if (copyObjectResponse == null) {
            return null;
        }
        
        String eTag = copyObjectResponse.getETag();
        
        return eTag;
    }
}
