package bosClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.GetObjectRequest;
import com.baidubce.services.bos.model.ObjectMetadata;

/**
 * 下载Object
 * 
 * @author Administrator
 * 
 */
public class DownloadUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * 获取bucket中的object
     * 
     * @throws IOException
     */
    public static void getObject(String bucketName, String objectKey) throws IOException {
        // 获取Object，返回结果为BosObject对象
        BosObject object = bosClient.getObject(bucketName, objectKey);
        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();
        System.out.println(meta.getETag());
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        System.out.println(objectContent.read());
        
        // 获取Object至文件中
        File file = new File("/path/to/file");
        bosClient.getObject(bucketName, objectKey, file);
        
        //只获取ObjectMetadata
        ObjectMetadata objectMetadata = bosClient.getObjectMetadata(bucketName, objectKey);
        System.out.println(objectMetadata.getContentType());
        
        // 关闭流
        objectContent.close();
    }
    
    /**
     * 获取bucket中的object(高级)
     * 
     * @throws IOException
     */
    public static void getObjectAdvanced(String bucketName, String objectKey) throws IOException {
        // 新建GetObjectRequest
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
        // 获取0~100字节范围内的数据
        getObjectRequest.setRange(0, 100);
        
        // 获取Object，返回结果为BosObject对象
        BosObject object = bosClient.getObject(getObjectRequest);
        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();
        System.out.println(meta.getETag());
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        System.out.println(objectContent.read());
        
        // 获取Object至文件中
        File file = new File("/path/to/file");
        bosClient.getObject(getObjectRequest, file);
        
        // 关闭流
        objectContent.close();
    }
}
