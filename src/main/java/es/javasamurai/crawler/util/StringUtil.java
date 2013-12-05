package es.javasamurai.crawler.util;

public class StringUtil {
	
	public static String clean(String number) {
		StringBuffer valueOk = new StringBuffer();
		
		for (int i = 0; i < number.length(); i++) {
			
			char c = number.charAt(i);
			if ((c != '.') && (c != ',')) {
				valueOk.append(c);
			}
			
		}
		
		return valueOk.toString();
	}
	
}