package jp.ac.hal.skymoons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utility {

	public static String nlToBR(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll("\n", "<br>");
		return str;
	}

	public void download(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		InputStream in = null;

		String path = request.getParameter("path");
		String fileName = request.getParameter("fileName");

		File file = new File(path);
		System.out.println(file.getName());
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");

			in = new FileInputStream(path);
			out = response.getOutputStream();
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = in.read(buff, 0, buff.length)) != -1) {
				out.write(buff, 0, len);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static String getFileImage(String fileName) {
		String image = "../images/icon/";
		String[] extension = fileName.split("\\.");

		switch (extension[extension.length - 1]) {
		case "txt":
			image += "txt.png";
			break;
		case "xls":
			image += "xls.png";
			break;
		case "xlsx":
			image += "xls.png";
			break;
		case "jpg":
			image += "jpg.png";
			break;
		case "jpeg":
			image += "jpg.png";
			break;
		case "pdf":
			image += "pdf.png";
			break;
		case "ppt":
			image += "ppt.png";
			break;
		case "pptx":
			image += "ppt.png";
			break;
		case "docx":
			image += "doc.png";
			break;
		case "doc":
			image += "doc.png";
			break;
		case "avi":
			image += "avi.png";
			break;
		case "png":
			image += "png.png";
			break;

		default:
			image += "file.png";
			break;
		}

		return image;
	}

}
