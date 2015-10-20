package bosClient;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * 测试批量上传
 * 
 * @author Administrator
 *
 */
public class TestBatchUpload {
	private static final String bucketName = "cndwineapp";
	private static final String baseDirectoryUnderBucket = "image/cgcode";
	private static final String baseFileDirectory = "D:/zhengjiabin/download/cgcode";

	/** 缩略图名称 */
	private static final String thumbnailName = "Thumbs.db";

	@Test
	public void batchUpload() throws IOException {
		File file = new File(baseFileDirectory);
		loopFiles(file);
	}

	private void loopFiles(File file) throws IOException {
		File[] listFiles = file.listFiles();
		for (File listFile : listFiles) {
			if (listFile.isDirectory()) {
				loopFiles(listFile);
			} else {
				putObject(listFile);
			}
		}
	}

	public void putObject(File file) {
		String name = file.getName();
		if (thumbnailName.equals(name)) {
			return;
		}

		String absolutePath = file.getAbsolutePath();
		absolutePath = absolutePath.replace("\\", "/");
		String relativeDirectory = absolutePath.replace(baseFileDirectory, "");
		String directoryUnderBucket = baseDirectoryUnderBucket + relativeDirectory;

		try {
			String directory = BosUploadUtil.putObject(bucketName, directoryUnderBucket, file);
			System.out.println(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
