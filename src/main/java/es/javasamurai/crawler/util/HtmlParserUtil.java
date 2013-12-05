package es.javasamurai.crawler.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlParserUtil {
	
	public static int getNumberResults(Document page) {
		String[] nodes = page.getElementsByAttributeValue("class", "result-summary same-name-dir").get(0).text().split(" ");
		if ((nodes[2].contains(".")) || (nodes[2].contains(","))) {
			nodes[2] = StringUtil.clean(nodes[2]);
		}
		return Integer.valueOf(nodes[2]).intValue();
	}

	public static String getTitle(Element element) {
		return element.select("a[title]").get(0).attr("title");
	}

	public static String getPublicUrl(Element element) {
		return element.select("a[title]").get(0).attr("href").trim();
	}

	public static String getLocation(Element element) {
		return element.select("span.location").text().trim();
	}

	public static String getIndustry(Element element) {
		return element.select("span.industry").text();
	}

	public static String getActiveJob(Element element) {
		if (element.select("dd.current-content").size() > 0) {
			return element.select("dd.current-content").get(0).text();
		}
		return null;
	}

	public static String getEducation(Element element) {
		if (element.select("dd.education-content").size() > 0) {
			return element.select("dd.education-content").get(0).text();
		}
		return null;
	}

}
