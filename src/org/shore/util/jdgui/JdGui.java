package org.shore.util.jdgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class JdGui {
	static File srcDir = new File("D:/doc/tingyun/tingyun-agent-java.src");
	static File destDir = new File("D:/doc/tingyun/proj/src");
	static int l = srcDir.getAbsolutePath().length();

	public static void main(String[] args) throws Exception {
		dir(srcDir);

	}

	public static void dir(File src) throws Exception {
		for (File f : src.listFiles()) {
			if (f.isDirectory()) {
				dir(f);
			} else {
				String s = f.getAbsolutePath().substring(l);
				File dest = new File(destDir, s);
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				if (f.getName().endsWith("java")) {
					java(f, dest);
				} else {
					resource(f, dest);
				}
			}
		}
	}

	public static void java(File f, File dest) throws IOException {
		InputStream is = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		OutputStream os = new FileOutputStream(dest);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s.startsWith("/* Location: ")
					|| s.startsWith(" * Qualified Name: ")
					|| s.startsWith(" * JD-Core Version: ") || s.equals(" */")) {
				continue;
			}
			if (s.startsWith("/*") && s.indexOf("*/") > 0) {
				s = s.substring(s.indexOf("*/") + 2);
			}

			pw.println(s);
		}
		pw.flush();
		is.close();
		os.close();
	}

	public static void resource(File f, File dest) throws IOException {
		InputStream is = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		OutputStream os = new FileOutputStream(dest);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
		char[] b = new char[1024];
		int len = 0;
		while ((len = br.read(b)) > 0) {
			pw.write(b, 0, len);

		}
		pw.flush();
		os.close();
		is.close();

	}

}
