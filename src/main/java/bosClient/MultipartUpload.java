package bosClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.AbortMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListMultipartUploadsRequest;
import com.baidubce.services.bos.model.ListMultipartUploadsResponse;
import com.baidubce.services.bos.model.ListPartsRequest;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.MultipartUploadSummary;
import com.baidubce.services.bos.model.PartETag;
import com.baidubce.services.bos.model.PartSummary;
import com.baidubce.services.bos.model.UploadPartRequest;
import com.baidubce.services.bos.model.UploadPartResponse;

/**
 * 分块上传工具
 * 
 * @author Administrator
 * 
 */
public class MultipartUpload {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
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
    public static void multipartUpload(String bucketName, String objectKey) throws IOException {
        //初始化分块上传组件
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        
        //开始上传
        List<PartETag> partETags = startMultipartUpload(initiateMultipartUploadResponse);
        
        //完成分块上传
        completeMultipartUpload(initiateMultipartUploadResponse, partETags);
    }
    
    /**
     * 初始化分块上传组件
     */
    private static InitiateMultipartUploadResponse initiateMultipartUploadResponse(String bucketName, String objectKey) {
        // 初始化multipart Upload
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = bosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        
        // 打印UploadId
        System.out.println("UploadId: " + initiateMultipartUploadResponse.getUploadId());
        
        return initiateMultipartUploadResponse;
    }
    
    /**
     * <pre>
     * 测试分块上传Object
     * 
     * 注意点：
     * 1、UploadPart 方法要求除最后一个Part以外，其他的Part大小都要大于5MB。
     * 2、但是Upload Part接口并不会立即校验上传Part的大小；只有当Complete Multipart Upload的时候才会校验。
     * 3、为了保证数据在网络传输过程中不出现错误，建议用户在收到BOS的返回请求后，用UploadPart返回的Content-MD5值验证上传数据的正确性。
     * 4、Part号码的范围是1~10000。如果超出这个范围，BOS将返回InvalidArgument的错误码。
     * 5、每次上传Part时都要把流定位到此次上传块开头所对应的位置。
     * 6、每次上传Part之后，BOS的返回结果会包含一个 PartETag 对象，它是上传块的ETag与块编号（PartNumber）的组合，在后续完成分块上传的步骤中会用到它，因此需要将其保存起来。
     * 7、一般来讲这些 PartETag 对象将被保存到List中
     * </pre>
     * 
     * @param initiateMultipartUploadResponse 分块上传配置组件
     * @throws IOException
     */
    private static List<PartETag> startMultipartUpload(InitiateMultipartUploadResponse initiateMultipartUploadResponse) throws IOException {
        // 设置每块为 5MB
        final long partSize = 1024 * 1024 * 5L;
        File partFile = new File("/path/to/file.zip");
        int partCount = getPartCount(partFile, partSize);
        
        // 新建一个List保存每个分块上传后的ETag和PartNumber
        List<PartETag> partETags = new ArrayList<PartETag>();
        
        UploadPartResponse uploadPartResponse = null;
        for (int i = 0; i < partCount; i++) {
            uploadPartResponse = uploadPart(partFile, partSize, i, initiateMultipartUploadResponse);
            
            // 将返回的PartETag保存到List中。
            partETags.add(uploadPartResponse.getPartETag());
        }
        
        return partETags;
    }
    
    /**
     * 每个分块的上传
     * 
     * @param partFile
     * @param partSize
     * @param currentPart
     * @param initiateMultipartUploadResponse
     * @return
     * @throws IOException
     */
    private static UploadPartResponse uploadPart(File partFile, long partSize, int currentPart, InitiateMultipartUploadResponse initiateMultipartUploadResponse) throws IOException {
        // 跳到每个分块的开头
        long skipBytes = partSize * currentPart;
        // 计算每个分块的大小
        long size = partSize < partFile.length() - skipBytes ? partSize : partFile.length() - skipBytes;
        
        // 获取文件流
        FileInputStream fis = new FileInputStream(partFile);
        fis.skip(skipBytes);
        
        // 创建UploadPartRequest，上传分块
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(initiateMultipartUploadResponse.getBucketName());
        uploadPartRequest.setKey(initiateMultipartUploadResponse.getKey());
        uploadPartRequest.setUploadId(initiateMultipartUploadResponse.getUploadId());
        uploadPartRequest.setInputStream(fis);
        uploadPartRequest.setPartSize(size);
        uploadPartRequest.setPartNumber(currentPart + 1);
        UploadPartResponse uploadPartResponse = bosClient.uploadPart(uploadPartRequest);
        
        // 关闭文件
        fis.close();
        
        return uploadPartResponse;
    }
    
    /**
     * 获取分块数目
     * 
     * @param partFile
     * @return
     */
    private static int getPartCount(File partFile, long partSize) {
        int partCount = (int)(partFile.length() / partSize);
        if (partFile.length() % partSize != 0) {
            partCount++;
        }
        
        return partCount;
    }
    
    /**
     * 完成分块上传
     * 
     * @throws IOException
     */
    private static void completeMultipartUpload(InitiateMultipartUploadResponse initiateMultipartUploadResponse, List<PartETag> partETags) throws IOException {
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(initiateMultipartUploadResponse.getBucketName(), initiateMultipartUploadResponse.getKey(), initiateMultipartUploadResponse.getUploadId(), partETags);
        
        // 完成分块上传
        CompleteMultipartUploadResponse completeMultipartUploadResponse = bosClient.completeMultipartUpload(completeMultipartUploadRequest);
        
        // 打印Object的ETag
        System.out.println(completeMultipartUploadResponse.getETag());
    }
    
    /**
     * 取消分块上传
     */
    public static void abortMultipartUpload(String bucketName, String objectKey) {
        //初始化分块上传组件
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId());
        
        // 取消分块上传
        bosClient.abortMultipartUpload(abortMultipartUploadRequest);
    }
    
    /**
     * 获取未完成的分块上传事件
     * 
     * @param bucketName
     */
    public static void listMultipartUploads(String bucketName) {
        ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
        
        // 获取Bucket内所有上传事件
        ListMultipartUploadsResponse listing = bosClient.listMultipartUploads(listMultipartUploadsRequest);
        
        // 遍历所有上传事件
        for (MultipartUploadSummary multipartUpload : listing.getMultipartUploads()) {
            System.out.println("Key: " + multipartUpload.getKey() + " UploadId: " + multipartUpload.getUploadId());
        }
    }
    
    /**
     * 获取所有已上传的块信息
     * 
     * @param bucketName
     * @param objectKey
     */
    public static void listParts(String bucketName, String objectKey) {
        //初始化分块上传组件
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId());
        
        // 获取上传的所有Part信息
        ListPartsResponse partListing = bosClient.listParts(listPartsRequest);
        
        // 遍历所有Part
        for (PartSummary part : partListing.getParts()) {
            System.out.println("PartNumber: " + part.getPartNumber() + " ETag: " + part.getETag());
        }
    }
}
