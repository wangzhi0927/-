package com.slzr.common.domain;

public class Data {
	   //图片路径    
	   private String src;    
	 
	   //图片标题    
	   private String title;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Data(String src, String title) {
		super();
		this.src = src;
		this.title = title;
	}
	   
	
	   
}
