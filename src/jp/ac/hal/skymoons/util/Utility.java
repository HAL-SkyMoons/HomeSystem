package jp.ac.hal.skymoons.util;

public class Utility {

	public static String nlToBR(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll("\n", "<br>");
		return str;
	}

}
