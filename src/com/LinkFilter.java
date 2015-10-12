package com;

public interface LinkFilter {

	//过滤提取出的URL，它使得程序中提取出的URL只会和猎兔网站相关，而不会提取其他无关的网站。
	public boolean accept(String url);
}
