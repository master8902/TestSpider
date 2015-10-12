package com;

import java.util.Set;

public class MyCrawler {
	
	/*
	 * 使用种子初始化URL队列
	 * */
	private void initCrawlerWithAeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++)
			LinkQueue.addUnvisitedUrl(seeds[i]);
	}
	/*
	 * 抓取过程
	 * */
	public void crawling(String[] seeds){
		//定义过滤器，提取以http://www.lietu.com开头的链接
		LinkFilter filter = new LinkFilter(){
			@Override
			public boolean accept(String url) {
				if(url.startsWith("http://www.baidu.com"))
					return true;
				else
					return false;
			}
			
		};
		//初始化URL队列
		initCrawlerWithAeeds(seeds);
		//循环条件：待抓取的链接不空且抓取的网页不多于1000
		while(!LinkQueue.unVisitedUrlsEmpty()&&LinkQueue.getVisitedUrlNum()<=100){
			//对头URL出队列
			String visiturl = (String)LinkQueue.unVisitedUrlDeQueue();
			if(visiturl==null)
				continue;
			DownLoadFile downloadFile = new DownLoadFile();
			//下载网页
			downloadFile.downloadFile(visiturl);
			//该URL放入已访问的URL中
			LinkQueue.addUnvisitedUrl(visiturl);
			//提取出下载网页中的URL
			Set<String> links = HtmlParserTool.extracLinks(visiturl, filter);
			//新的未访问的URL队列
			for(String link:links){
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}
	
	
	public static void main(String[] args){
		MyCrawler crawler = new MyCrawler();
		crawler.crawling(new String[]{"http://www.baidu.com"});
		System.out.println("爬取结束");
	}
}
