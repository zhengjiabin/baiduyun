package bosClient;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * ≤‚ ‘≈˙¡ø…æ≥˝
 * @author Administrator
 *
 */
public class TestBatchDelete {
	private static final String bucketName = "cndwineapp";
	private static final String baseDirectoryUnderBucket = "image/cgcode";
	private static final String baseFileDirectory = "D:/zhengjiabin/download/cgcode";

	@Test
	public void batchDelete() throws IOException {
		File file = new File(baseFileDirectory);
		loopFiles(file);
	}

	private void loopFiles(File file) throws IOException {
		File[] listFiles = file.listFiles();
		for (File listFile : listFiles) {
			if (listFile.isDirectory()) {
				loopFiles(listFile);
			} else {
				deleteObject(listFile);
			}
		}
	}

	public void deleteObject(File file) throws IOException {
		String absolutePath = file.getAbsolutePath();
		absolutePath = absolutePath.replace("\\", "/");
		String relativeDirectory = absolutePath.replace(baseFileDirectory, "");
		String directoryUnderBucket = baseDirectoryUnderBucket + relativeDirectory;
		
		BosOperateObjectUtil.deleteObject(bucketName, directoryUnderBucket);
	}
}
