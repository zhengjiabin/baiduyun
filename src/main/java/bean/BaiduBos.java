package bean;

/**
 * 百度BOS对象
 * 
 * @author Administrator
 * 
 */
public class BaiduBos {
    private String bucketName;
    
    private String newFileName;
    
    private String filePath;
    
    public BaiduBos() {
        super();
    }
    
    public String getBucketName() {
        return bucketName;
    }
    
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    public String getNewFileName() {
        return newFileName;
    }
    
    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
