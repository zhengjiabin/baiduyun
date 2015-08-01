package bosClient;

import java.io.IOException;

import org.junit.Test;

import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListPartsRequest;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.PartSummary;

/**
 * 测试分块上传
 * 
 * @author Administrator
 * 
 */
public class TestMultipartUpload {
    /**
     * <pre>
     * 分块上传Object
     * 应用场景如下：
     * 1、需要支持断点上传。
     * 2、上传超过5GB大小的文件。
     * 3、网络条件较差，和BOS的服务器之间的连接经常断开。
     * 4、需要流式地上传文件。
     * 5、上传文件之前，无法确定上传文件的大小。
     * </pre>
     * 
     * @throws IOException
     */
    @Test
    public void multipartUpload() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.multipartUpload(bucketName, objectKey);
    }
    
    /**
     * 取消分块上传
     */
    @Test
    public void abortMultipartUpload() {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.abortMultipartUpload(bucketName, objectKey);
    }
    
    /**
     * 获取未完成的分块上传事件
     * 
     */
    @Test
    public void listMultipartUploads() {
        String bucketName = "";
        
        MultipartUpload.listMultipartUploads(bucketName);
    }
    
    /**
     * 获取所有已上传的块信息
     * 
     */
    @Test
    public void listParts() {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.listParts(bucketName, objectKey);
    }
}
