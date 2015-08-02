package bosClient;

import java.io.IOException;

import org.junit.Test;

/**
 * 测试下载Object
 * 
 * @author Administrator
 * 
 */
public class TestDownload {
    /**
     * 获取bucket中的object
     * 
     * @throws IOException
     */
    @Test
    public void getObject() throws IOException {
        String bucketName = "binbinpictures";
        String objectKey = "test";
        
        DownloadUtil.getObject(bucketName, objectKey);
    }
    
    /**
     * 获取bucket中的object(高级)
     * 
     * @throws IOException
     */
    @Test
    public void getObjectAdvanced() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        DownloadUtil.getObjectAdvanced(bucketName, objectKey);
    }
}
