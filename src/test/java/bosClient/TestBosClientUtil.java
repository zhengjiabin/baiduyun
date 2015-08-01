package bosClient;

import org.junit.Test;

import com.baidubce.services.bos.BosClient;

/**
 * ≤‚ ‘∞Ÿ∂»‘∆BOS
 * 
 * @author Administrator
 * 
 */
public class TestBosClientUtil {
    
    @Test
    public void testInitBosClient() {
        BosClient bosClient = BosClientUtil.getInstance();
        System.out.println(bosClient.getServiceId());
    }
}
