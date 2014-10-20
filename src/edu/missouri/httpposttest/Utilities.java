package edu.missouri.httpposttest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.os.Environment;

public class Utilities {
	public static final String HTTP_ADDRESS = "http://dslsrv8.cs.missouri.edu/webapps/Crt2/writeArrayToFile.php";
	public static final String HTTPS_ADDRESS = "https://dslsrv8.cs.missouri.edu/webapps/Crt2/writeArrayToFile.php";

	public static final String TAG_HTTP = "HTTPTestActivity";
	public static final String TAG_HTTPS = "HTTPSTestActivity";
	public static final String TAG_HTTPS_FULL = "HTTPSFullTestActivity";
	public static final String TAG_READER = "TestFileReader";

	public static final String LARGE_PATH = "test.txt";
	public static final String SMALL_PATH = "chestData0002.txt";
	public static final String XL_PATH = "chest.txt";
	public static final String XL_FILENAME = "xl";
	public static final String SMALL_FILENAME = "s";
	public static final String LARGE_FILENAME = "l";

	public static void writeToFile(String fileName, String input) {
		Writer writer = null;
		File sdcard = Environment.getExternalStorageDirectory();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(sdcard, fileName), true), "utf-8"));
			writer.write(input + "\r\n");
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
	}

}

