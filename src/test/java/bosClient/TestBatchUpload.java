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
	private static final String baseDirectoryUnderBucket = "image/cgcode/";
	private static final String baseFileDirectory = "E:/tupian";

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

		String relativeDirectory = getDirectory(file);
		if (relativeDirectory == null) {
			return;
		}
		String directoryUnderBucket = baseDirectoryUnderBucket + relativeDirectory;

		try {
			String directory = BosUploadUtil.putObject(bucketName, directoryUnderBucket, file);
			System.out.println(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件相对路径
	 * 
	 * @param file
	 * @return
	 */
	private String getDirectory(File file) {
		// String absolutePath = file.getAbsolutePath();
		// absolutePath = absolutePath.replace("\\", "/");
		// String relativeDirectory = absolutePath.replace(baseFileDirectory,
		// "");

		String name = file.getName();
		int suffix = name.lastIndexOf(".");
		String prefix = name.substring(0, suffix);
		if (prefix == null || prefix.length() <= 3) {
			return null;
		}
		String packPath = prefix.substring(0, prefix.length() - 3);

		return packPath + "/" + name;
	}
}
