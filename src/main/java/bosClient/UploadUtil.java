package bosClient;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;

/**
 * 上传Object
 * 
 * @author Administrator
 * 
 */
public class UploadUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * <pre>
     * 测试文件上传
     * 1、putObject函数支持不超过5GB的Object上传
     * 2、BOS会在Header中返回Object的ETag作为文件标识
     * </pre>
     * 
     * @throws IOException
     */
    public static void putObject(String bucketName, String objectKey) throws IOException {
        // 以文件形式上传Object
        File file = new File("F:/temp/ceshi.png");
        PutObjectResponse putObjectFromFileResponse = bosClient.putObject(bucketName, objectKey, file);
        System.out.println(putObjectFromFileResponse.getETag());
        
        // 以数据流形式上传Object
        //        InputStream inputStream = new FileInputStream(file);
        //        PutObjectResponse putObjectResponseFromInputStream = bosClient.putObject(bucketName, objectKey, inputStream);
        //        System.out.println(putObjectResponseFromInputStream.getETag());
        
        // 以二进制串上传Object，read只读一行，在此仅用于测试
        //        byte[] b = new byte[] {};
        //        inputStream.read(b);
        //        PutObjectResponse putObjectResponseFromByte = bosClient.putObject(bucketName, objectKey, b);
        //        System.out.println(putObjectResponseFromByte.getETag());
        
        // 以字符串上传Object
        //        String content = "";
        //        PutObjectResponse putObjectResponseFromString = bosClient.putObject(bucketName, objectKey, content);
        //        System.out.println(putObjectResponseFromString.getETag());
    }
    
    /**
     * 测试文件上传
     */
    public static void putObjectAdvanced(String bucketName, String objectKey) {
        File file = new File("/path/to/file.zip");
        
        // 初始化上传输入流
        ObjectMetadata metadata = new ObjectMetadata();
        // 设置ContentLength大小
        metadata.setContentLength(1000);
        // 设置自定义元数据name的值为my-data
        metadata.addUserMetadata("name", "my-data");
        // 设置ContentType
        metadata.setContentType(MediaType.APPLICATION_JSON);
        
        bosClient.putObject(bucketName, objectKey, file, metadata);
    }
}
