package com.slzr.operation.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.utils.R;
import com.slzr.operation.domain.DailysummarySettleDO;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.UserDO;
import com.slzr.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.slzr.common.config.BootdoConfig;
import com.slzr.common.controller.BaseController;
import com.slzr.common.domain.Data;
import com.slzr.common.domain.FileDO;
import com.slzr.common.domain.ImageUploadResult;
import com.slzr.common.utils.DWMQDateUtil;
import com.slzr.common.utils.DWMQDateVO;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.FileType;
import com.slzr.common.utils.FileUtil;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.PathUtils;
import com.slzr.common.utils.Query;
import com.slzr.operation.domain.ArticleDO;
import com.slzr.operation.domain.DailysummaryTopupDO;
import com.slzr.operation.service.ArticleService;

@RequestMapping("/operation/article")
@Controller
public class ArticleController extends BaseController{
	private String prefix = "operation/article";
	@Autowired
	private ArticleService articleservice;

	@Autowired
	private UserService userService;
	
	@Autowired
	BootdoConfig bootdoConfig;

	@Autowired
	private MerchService merchService;
	
	@GetMapping("/list")
	@SystemControllerLog(description="0")
	public String list(Model model) {
		HashMap<String,Object> params=new HashMap<>();
		UserDO userDO = userService.get(getId());
		List<MerchDo> merchantList=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			merchantList=merchService.list(params);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			merchantList=merchService.list(params);
		}
		DWMQDateVO dwmq = null;
		try {
			dwmq = DWMQDateUtil.GetDWMQDateVO(DateUtils.format(Calendar.getInstance().getTime(), DateUtils.DATE_PATTERN));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("dwmq", dwmq);
		model.addAttribute("merchantList",merchantList);
		return prefix + "/list";
	}


	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model,@PathVariable("id") String id) {
		model.addAttribute("model",articleservice.get(id));
		return prefix + "/edit";
	}
	
	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:article:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		UserDO userDO = userService.get(getId());
		PageUtils pageUtil=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			//查询列表数据
			Query query = new Query(params);
			List<ArticleDO> list = articleservice.list(query);
			int total = articleservice.count(query);
			 pageUtil = new PageUtils(list, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			//查询列表数据
			Query query = new Query(params);
			List<ArticleDO> list = articleservice.list(query);
			int total = articleservice.count(query);
			 pageUtil = new PageUtils(list, total);
		}
		return pageUtil;
	}


	@ResponseBody
	@SystemControllerLog(description="1")
	@PostMapping("api/add")
	public R apiadd(@RequestParam Map<String, Object> params,ArticleDO article){
		String arttype = article.getArttype();
		String arttitle = article.getArttitle();
		String artsummary = article.getArtsummary();
		String content = params.get("content").toString();
		String imgpath = article.getImgpath()!=null?article.getImgpath():"";
		
		if(StringUtils.isNullOrEmpty(arttype))
		{
			return R.error("文章类型不能为空");
		}
		if(StringUtils.isNullOrEmpty(arttitle))
		{
			return R.error("文章标题不能为空");
		}
		if(StringUtils.isNullOrEmpty(artsummary))
		{
			return R.error("文章摘要不能为空");
		}
		if(StringUtils.isNullOrEmpty(content))
		{
			return R.error("正文内容不能为空");
		}
		
		articleservice.add(arttype, arttitle, artsummary,content,imgpath, this.getId().intValue());
		return R.ok("添加成功");
	}
	
	@ResponseBody
	@SystemControllerLog(description="2")
	@PostMapping("api/edit")
	public R apiedit(@RequestParam Map<String, Object> params,ArticleDO article){
		int id = Integer.parseInt(params.get("id").toString());
		String arttype = article.getArttype();
		String arttitle = article.getArttitle();
		String artsummary = article.getArtsummary();
		String content = params.get("content").toString();
		String imgpath = article.getImgpath()!=null?article.getImgpath():"";
		articleservice.edit(id,arttype, arttitle, artsummary,content,imgpath, this.getId().intValue());
		return R.ok("添加成功");
	}
	
	@ResponseBody
	@PostMapping("api/uploadImg")
	public R apiuploadImg(@RequestParam("files") MultipartFile file){
        if (!file.isEmpty()) {
        	String nid=UUID.randomUUID().toString();
        	String extname = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),file.getOriginalFilename().length());
            String nname=nid+extname;
    		String fileName = file.getOriginalFilename();
    		fileName = FileUtil.renameToUUID(fileName);
    		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
    		try {
    			FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
    		} catch (Exception e) {
    			return R.error("上传失败," + e.getMessage());
    		}
            
            Map<String, Object> params=new HashMap<String, Object>();
            params.put("msg", "上传成功");
            params.put("fileName",sysFile.getUrl());
            params.put("id", nname);
            return R.ok(params);
        } else {
            return R.error("上传失败，因为文件是空的.");      
        }
	}
	
	/**
	 * 富文本上传图片
	 * @param imgFile
	 * @return
	 */
	   @ResponseBody
	   @PostMapping("image/single")    
	   public ImageUploadResult uploadImgSingle (@RequestParam("file") MultipartFile imgFile) {        
  		String fileName = imgFile.getOriginalFilename();
  		fileName = FileUtil.renameToUUID(fileName);
  		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
  		
	    ImageUploadResult result = new ImageUploadResult();        	      
  		try {
  			FileUtil.uploadFile(imgFile.getBytes(), bootdoConfig.getUploadPath(), fileName);
  			result.setCode(0);        
  			result.setMsg(null);        
  			result.setData(new Data( sysFile.getUrl(), fileName ));  
  		} catch (Exception e) {
  			result.setCode(-1); 
  			result.setMsg("上传失败");
  		}      
	      return result;    
	   }


	/**
	 * 标记 删除
	 */
	@PostMapping("/deleteart")
	@SystemControllerLog(description="3")
	@ResponseBody
	//	@RequiresPermissions("operation:GuestMessage:remove")
	public R deleteart(Integer id){
		try {
//			GuestMessageDO Do = GuestMessageService.getByID(id);
			articleservice.remove(id);
			return R.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}
	
}
