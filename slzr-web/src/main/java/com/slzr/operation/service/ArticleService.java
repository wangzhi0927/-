package com.slzr.operation.service;

import java.util.List;
import java.util.Map;

import com.slzr.operation.domain.ArticleDO;

public interface ArticleService{

	List<ArticleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	ArticleDO get(String id);


	int remove(Integer ID);

	void add(String arttype, String arttitle, String artsummary,String content,String imgpath,int userid);

	void edit(int id,String arttype, String arttitle, String artsummary,String content,String imgpath,int userid);

}