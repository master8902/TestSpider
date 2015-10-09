package com;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {

	private static Set visitedUrl = new HashSet();
	
	private static Queue unVisitedUrl = new  Queue();
	
	public static Queue getUnVisitedUrl(){
		return unVisitedUrl;
	}
	
	public static void removeVisitedUrl(String url){
		visitedUrl.remove(url);
	}
	
	public static Object unVisitedUrlDeQueue(){
		return unVisitedUrl.deQueue();
	}
	
	public static void addUnvisitedUrl(String url){
		if( url != null && !url.trim().equals("")
				&& !visitedUrl.contains(url)
				&& !unVisitedUrl.contians(url))
			unVisitedUrl.enQueue(url);
	}
	
	public static int getVisitedUrlNum(){
		return visitedUrl.size();
	}
	
	public static boolean unVisitedUrlsEmpty(){
		return unVisitedUrl.empty();
	}
}
