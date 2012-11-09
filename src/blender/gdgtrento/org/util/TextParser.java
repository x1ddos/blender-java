package blender.gdgtrento.org.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that works with text
 */
public class TextParser {
	
	public static final Pattern RE_URL = Pattern.compile("https?://[^\\s]+");

	/**
	 * Finds all text chunks that looks like URLs.
	 * @return in case no URLs were found returns empty list.
	 */
	public static List<String> extractLinksFromText(String text) {
		List<String> links = new ArrayList<String>();
		Matcher m = RE_URL.matcher(text);
		while (m.find()) {
			String url = m.group();
			char c = url.charAt(url.length()-1); // last char
			if (c == ')' || c == '.' || c == '"') {
				url = url.substring(0, url.length()-1);
			}
			links.add(url);
		}
		return links;
	}	
}
