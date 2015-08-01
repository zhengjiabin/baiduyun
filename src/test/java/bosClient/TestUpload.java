package bosClient;

import java.io.IOException;

import org.junit.Test;

/**
 * 测试上传Object
 * 
 * @author Administrator
 * 
 */
public class TestUpload {
    /**
     * <pre>
     * 测试文件上传
     * 1、putObject函数支持不超过5GB的Object上传
     * 2、BOS会在Header中返回Object的ETag作为文件标识
     * </pre>
     * 
     * @throws IOException
     */
    @Test
    public void putObject() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        UploadUtil.putObject(bucketName, objectKey);
    }
    
    /**
     * 测试文件上传
     */
    @Test
    public void putObjectAdvanced() {
        String bucketName = "";
        String objectKey = "";
        
        UploadUtil.putObjectAdvanced(bucketName, objectKey);
    }
}
