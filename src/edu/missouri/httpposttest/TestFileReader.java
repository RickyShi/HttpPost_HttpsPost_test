package edu.missouri.httpposttest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class TestFileReader {
	private BufferedReader br;
	File sdcard = Environment.getExternalStorageDirectory();
	TestFileReader() {
		this.br = null;
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public String rtnString(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(new File(sdcard, path)));

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine).append("\r\n");
				// Log.d(Utilities.TAG_READER, "line: " + sb.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * Using Asset folder
	 * when file is too big, it may read less.
	 *
	 * @param path
	 * @param ctx
	 * @return
	 */
	public String rtnString2(String path, Context ctx) {
		String str = "";
		try {
			InputStream is = ctx.getAssets().open(path);

			// We guarantee that the available method returns the total
			// size of the asset... of course, this does mean that a single
			// asset can't be more than 2 gigs.
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			str = new String(buffer);
			Log.d(Utilities.TAG_READER, str);

		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
		return str;
	}

}
