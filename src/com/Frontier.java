package com;

public interface Frontier {

	public CrawlUrl getNext() throws Exception;
	public boolean putUrl(CrawlUrl url);
}
