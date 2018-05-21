package com.slzr.operation.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.slzr.common.annotation.Log;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.operation.domain.GuestMessageReplayDo;
import com.slzr.operation.service.Impl.GuestMessageReplayServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.operation.domain.GuestMessageDO;
import com.slzr.operation.service.GuestMessageService;
@Controller
@RequestMapping("/operation/guestmessage")
public class GuestMessageController extends BaseController{
	@Autowired
	private GuestMessageService GuestMessageService;
	@Autowired
	private GuestMessageReplayServiceImpl guestMessageReplayServiceImpl;
	
	@GetMapping()
	@SystemControllerLog(description="0")
	@RequiresPermissions("operation:guestmessage:guestmessage")
	String Guestmessage(){
	    return "operation/guestmessage/guestmessage";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("operation:guestmessage:guestmessage")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GuestMessageDO> guestmessageList = GuestMessageService.list(query);
		int total = GuestMessageService.count(query);
		PageUtils pageUtils = new PageUtils(guestmessageList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("operation:guestmessage:add")
	String add(){
	    return "operation/guestmessage/add";
	}


	
//	@RequiresPermissions("sys:menu:edit")
	// mid 用户ID gid 投诉 GuestMessage ID 回复ID rid
	@GetMapping("/edit/{mid}/{gid}/{name}/{msgcontent}/{mobilephone}/{rid}/{replycontent}")
	public  String edit(Model model, @PathVariable("mid") int mid, @PathVariable("gid") int gid, @PathVariable("name") String name, @PathVariable("msgcontent") String msgcontent, @PathVariable("mobilephone") String mobilephone, @PathVariable("rid") int rid, @PathVariable(value = "replycontent",required=false ) String replycontent) {
		GuestMessageDO mGuestMessageDO = new GuestMessageDO();
		mGuestMessageDO.setMid(mid);
		mGuestMessageDO.setGid(gid);
		if(name !=null && !name.equalsIgnoreCase("null"))mGuestMessageDO.setNickname(name);
		if(msgcontent !=null && !msgcontent.equalsIgnoreCase("null"))mGuestMessageDO.setMsgcontent(msgcontent);
		if(mobilephone !=null && !mobilephone.equalsIgnoreCase("null"))mGuestMessageDO.setMobilephone(mobilephone);
		mGuestMessageDO.setRid(rid);
		if(replycontent !=null && !replycontent.equalsIgnoreCase("null"))mGuestMessageDO.setReplycontent(replycontent);
		model.addAttribute("mGuestMessageDO", mGuestMessageDO);
		return "operation/guestmessage/guestMessageReply";
	}
	
	/**
	 * 回复
	 */
	@PostMapping("/replay")
	@SystemControllerLog(description="15")
	@ResponseBody
	public R replay(@RequestParam Map<String, String> map) {
		try {
			String mid = map.get("mid");
			String gid = map.get("gid");
			String rid = map.get("rid");
			String replayContent = map.get("replycontent");
			String replayDateTime = map.get("replayDateTime");

			if (rid != null && !"0".equals(rid)) {

				GuestMessageDO Do  = this.GuestMessageService.getByID(Integer.valueOf(gid).intValue());
				Do.setLastreplyuid(getId().intValue());
				Do.setLastreplydatetime(new Date());
				Do.setIsresolved(true);
				this.GuestMessageService.update(Do);

				GuestMessageReplayDo DoRpL = this.guestMessageReplayServiceImpl.getByID(Integer.valueOf(rid).intValue());
				DoRpL.setReplycontent(replayContent);
				DoRpL.setReplydatetime(new Date());
				this.guestMessageReplayServiceImpl.update(DoRpL);

			} else {
				GuestMessageDO Do  = this.GuestMessageService.getByID(Integer.valueOf(gid).intValue());
				Do.setLastreplyuid(getId().intValue());
				Do.setLastreplydatetime(new Date());
				this.GuestMessageService.update(Do);

				GuestMessageReplayDo DoRpL = new GuestMessageReplayDo();
				DoRpL.setReplycontent(replayContent);
				DoRpL.setMsgid(gid);
				this.guestMessageReplayServiceImpl.save(DoRpL);
			}
			return R.ok();
		} catch (Exception e) {
			return R.error();
		}
	}
	

    /**
     * 添加
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestParam Map<String, String> map) {
        try {
            String mobilephone = map.get("mobilephone");
            String truename = map.get("truename");
            String gender = map.get("gender");
            String msgcontent = map.get("msgcontent");
//            String attachphotourl = map.get("attachphotourl");
            String submitdatetime = map.get("submitdatetime");
            GuestMessageDO Do  = new GuestMessageDO();
            Do.setMobilephone(mobilephone);
            Do.setTruename(truename);
            Do.setGender(gender);
            Do.setMsgcontent(msgcontent);
//            Do.setAttachphotourl(attachphotourl);
            Do.setSubmitdatetime(new Date());
            this.GuestMessageService.update(Do);

            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }


	/**
	 * 标记 删除
	 */
	@PostMapping("/markIsDelete")
	@SystemControllerLog(description="3")
	@ResponseBody
//	@RequiresPermissions("operation:GuestMessage:remove")
	public R markIsDelete(Integer id){
		try {
//			GuestMessageDO Do = GuestMessageService.getByID(id);
			GuestMessageService.remove(id);
			return R.ok();
		}catch (Exception e) {
			return R.error();
		}
	}

}
