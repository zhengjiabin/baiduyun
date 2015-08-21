package bosClient;

import java.io.IOException;
import java.util.Properties;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;

public class BosClientUtil {

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

	/**
	 * 初始化BOS对象
	 * 
	 * @return
	 */
	private static BosClient initBosClient() {
		// 初始化属性对象
		Properties prop = initProperties();
		// 初始化配置对象
		BosClientConfiguration config = initBosClientConfiguration(prop);

		bosClient = new BosClient(config);
		return bosClient;
	}

	/**
	 * 设置百度云BOS配置对象
	 * 
	 * @param properties
	 * @return
	 */
	private static BosClientConfiguration initBosClientConfiguration(Properties prop) {
		BosClientConfiguration config = new BosClientConfiguration();

		// 设置代理
		setProxy(config, prop);
		// 设置需要用户验证的代理
		setProxyByValidate(config, prop);
		// 设置网络参数
		setNetwork(config, prop);

		// 设置登录信息
		setCredentials(config, prop);
		// 设置域名信息
		setEndpoint(config, prop);

		return config;
	}

	/**
	 * 获取百度云BOS配置属性
	 * 
	 * @return
	 */
	private static Properties initProperties() {
		Properties prop = new Properties();
		try {
			prop.load(BosClientUtil.class.getResourceAsStream("/baiduBos.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * 设置域名信息
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setEndpoint(BosClientConfiguration config, Properties prop) {
		String setEndpoint = prop.getProperty("setEndpoint");
		if (!Boolean.parseBoolean(setEndpoint)) {
			return;
		}

		/** 用户自己指定的域名 */
		String ENDPOINT = prop.getProperty("ENDPOINT");
		config.setEndpoint(ENDPOINT);
	}

	/**
	 * 设置登录信息
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setCredentials(BosClientConfiguration config, Properties prop) {
		/** 用户的Access Key ID */
		String ACCESS_KEY_ID = prop.getProperty("ACCESS_KEY_ID");
		/** 用户的Secret Access Key */
		String SECRET_ACCESS_KEY = prop.getProperty("SECRET_ACCESS_KEY");

		DefaultBceCredentials bceCredentials = new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
		config.setCredentials(bceCredentials);
	}

	/**
	 * 设置网络参数
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setNetwork(BosClientConfiguration config, Properties prop) {
		String setNetwork = prop.getProperty("setNetwork");
		if (!Boolean.parseBoolean(setNetwork)) {
			return;
		}

		// 设置HTTP最大连接数为10
		String maxConnections = prop.getProperty("maxConnections");
		config.setMaxConnections(Integer.parseInt(maxConnections));

		// 设置TCP连接超时为5000毫秒
		String connectionTimeoutInMillis = prop.getProperty("connectionTimeoutInMillis");
		config.setConnectionTimeoutInMillis(Integer.parseInt(connectionTimeoutInMillis));

		// 设置Socket传输数据超时的时间为2000毫秒
		String socketTimeoutInMillis = prop.getProperty("socketTimeoutInMillis");
		config.setSocketTimeoutInMillis(Integer.parseInt(socketTimeoutInMillis));
	}

	/**
	 * 设置需要用户验证的代理
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setProxyByValidate(BosClientConfiguration config, Properties prop) {
		String setProxyByValidate = prop.getProperty("setProxyByValidate");
		if (!Boolean.parseBoolean(setProxyByValidate)) {
			return;
		}

		String proxyUsername = prop.getProperty("proxyUsername");
		config.setProxyUsername(proxyUsername);

		String proxyPassword = prop.getProperty("proxyPassword");
		config.setProxyPassword(proxyPassword);
	}

	/**
	 * 设置代理
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setProxy(BosClientConfiguration config, Properties prop) {
		String setProxy = prop.getProperty("setProxy");
		if (!Boolean.parseBoolean(setProxy)) {
			return;
		}

		String proxyHost = prop.getProperty("proxyHost");
		config.setProxyHost(proxyHost);

		String proxyPort = prop.getProperty("proxyPort");
		config.setProxyPort(Integer.parseInt(proxyPort));
	}
}
