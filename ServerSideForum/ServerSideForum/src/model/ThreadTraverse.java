package model;

import java.util.ArrayList;

public class ThreadTraverse {
	public ArrayList<String> pages;

	public ThreadTraverse(String link, int p, int page) {
		pages = new ArrayList<>();
		addPages(link, p, page);
	}

	public void addPages(String link, int p, int page) {
		for(int i = 1; i<p+1; i++)
			if(i!=page)
				pages.add("<a href=\"" + link + "page-" +  i + "\">" + i + "</a>");
			else
				pages.add("...");
	}

	public ArrayList<String> getPages() {
		return pages;
	}
}