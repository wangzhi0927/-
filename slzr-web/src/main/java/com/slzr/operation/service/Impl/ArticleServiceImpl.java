package com.slzr.operation.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slzr.operation.dao.ArticleDao;
import com.slzr.operation.dao.DailysummarySettleDao;
import com.slzr.operation.domain.ArticleDO;
import com.slzr.operation.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleDao articleDao;

	@Override
	public List<ArticleDO> list(Map<String, Object> map) {
		return articleDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return articleDao.count(map);
	}

	@Override
	public ArticleDO get(String id) {
		int value = Integer.parseInt(id);
		return articleDao.get(value);
	}

	public int remove(Integer ID) {
		return articleDao.remove(ID);

	}
	
	@Override
	public void add(String arttype, String arttitle, String artsummary, String content, String imgpath,int userid) {
		articleDao.add(arttype, arttitle, artsummary,content,imgpath,userid);
	}

	@Override
	public void edit(int id, String arttype, String arttitle, String artsummary, String content,String imgpath, int userid) {
		articleDao.edit(id,arttype, arttitle, artsummary,content,imgpath,userid);
	}

}
