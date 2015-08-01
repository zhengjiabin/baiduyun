package bosClient;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;

public class BosClientUtil {
    /** 用户的Access Key ID */
    private static String ACCESS_KEY_ID = "c5f66ab5ad63445da2965fa894b2a983";
    
    /** 用户的Secret Access Key */
    private static String SECRET_ACCESS_KEY = "7235e1484c6f46f28ed6b57ed868efe3";
    
    /** 百度云 BOS客户端 */
    private volatile static BosClient bosClient;
    
    private BosClientUtil() {
        
    }
    
    /**
     * 初始化bosClient
     * 
     * @return
     */
    public static BosClient getInstance() {
        if (bosClient == null) {
            synchronized (BosClientUtil.class) {
                if (bosClient == null) {
                    bosClient = initBosClient();
                }
            }
        }
        return bosClient;
    }
    
    private static BosClient initBosClient() {
        BosClientConfiguration config = new BosClientConfiguration();
        
        // 配置代理
        //        config.setProxyHost("127.0.0.1");
        //        config.setProxyPort(8080);
        //设置需要用户验证的代理
        //        config.setProxyUsername("username");
        //        config.setProxyPassword("password");
        
        //配置网络参数
        // 设置HTTP最大连接数为10
        //        config.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒
        //        config.setConnectionTimeoutInMillis(5000);
        // 设置Socket传输数据超时的时间为2000毫秒
        //        config.setSocketTimeoutInMillis(5000);
        
        DefaultBceCredentials bceCredentials = new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        config.setCredentials(bceCredentials);
        //        config.setEndpoint(ENDPOINT);
        
        bosClient = new BosClient(config);
        
        return bosClient;
    }
}
