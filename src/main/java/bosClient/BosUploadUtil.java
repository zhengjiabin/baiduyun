package bosClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.springframework.util.StringUtils;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.ObjectMetadata;

/**
 * 上传Object
 * 
 * @author Administrator
 * 
 */
public class BosUploadUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * <pre>
     * 文件路径形式上传文件
     * 1、putObject函数支持不超过5GB的Object上传
     * 2、BOS会在Header中返回Object的ETag作为文件标识
     * </pre>
     * 
     * @throws IOException
     */
    public static String putObject(String bucketName, String directoryUnderBucket, String filePath) throws IOException {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || StringUtils.isEmpty(filePath)) {
            return null;
        }
        
        if (!BosBucketUtil.doesBucketExist(bucketName)) {
            BosBucketUtil.createBucket(bucketName);
        }
        
        // 以文件形式上传Object
        File file = new File(filePath);
        bosClient.putObject(bucketName, directoryUnderBucket, file);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        return directory;
    }
    
    /**
     * 文件内容字符串形式上传文件
     * 
     * @param bucketName
     * @param directoryUnderBucket
     * @param content
     */
    public static String pubObjectByString(String bucketName, String directoryUnderBucket, String content) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || StringUtils.isEmpty(content)) {
            return null;
        }
        
        if (!BosBucketUtil.doesBucketExist(bucketName)) {
            BosBucketUtil.createBucket(directoryUnderBucket);
        }
        
        // 以字符串上传Object
        bosClient.putObject(bucketName, directoryUnderBucket, content);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        return directory;
    }
    
    /**
     * 二进制形式上传文件
     * 
     * @param bucketName
     * @param directoryUnderBucket
     * @param b
     */
    public static String pubObject(String bucketName, String directoryUnderBucket, byte[] b) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || b == null) {
            return null;
        }
        
        if (!BosBucketUtil.doesBucketExist(bucketName)) {
            BosBucketUtil.createBucket(bucketName);
        }
        
        // 以二进制串上传Object，read只读一行，在此仅用于测试
        bosClient.putObject(bucketName, directoryUnderBucket, b);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        return directory;
    }
    
    /**
     * 数据流形式上传文件
     * 
     * @param bucketName
     * @param directoryUnderBucket
     * @param inputStream
     */
    public static String pubObject(String bucketName, String directoryUnderBucket, InputStream inputStream) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || inputStream == null) {
            return null;
        }
        
        if (!BosBucketUtil.doesBucketExist(bucketName)) {
            BosBucketUtil.createBucket(bucketName);
        }
        
        // 以数据流形式上传Object
        bosClient.putObject(bucketName, directoryUnderBucket, inputStream);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        return directory;
    }
    
    /**
     * 文件路径形式上传文件（高级）
     * 
     * @param bucketName
     * @param directoryUnderBucket
     * @param filePath
     */
    public static String putObjectAdvanced(String bucketName, String directoryUnderBucket, String filePath) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || StringUtils.isEmpty(filePath)) {
            return null;
        }
        
        if (!BosBucketUtil.doesBucketExist(bucketName)) {
            BosBucketUtil.createBucket(bucketName);
        }
        
        File file = new File(filePath);
        ObjectMetadata metadata = initObjectMetadata();
        bosClient.putObject(bucketName, directoryUnderBucket, file, metadata);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        return directory;
    }
    
    /**
     * 初始化上传输入流
     * 
     * @return
     */
    private static ObjectMetadata initObjectMetadata() {
        // 初始化上传输入流
        ObjectMetadata metadata = new ObjectMetadata();
        
        // 设置ContentLength大小
        metadata.setContentLength(1000);
        // 设置自定义元数据name的值为my-data
        metadata.addUserMetadata("name", "my-data");
        // 设置ContentType
        metadata.setContentType(MediaType.APPLICATION_JSON);
        
        return metadata;
    }
}
