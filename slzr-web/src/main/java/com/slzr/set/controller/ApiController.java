package com.slzr.set.controller;


import com.slzr.common.annotation.Log;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.config.Constant;
import com.slzr.common.controller.BaseController;
import com.slzr.common.domain.DictDO;
import com.slzr.common.service.DictService;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.set.domain.ApiDo;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.ApiService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/Api/apiSet")
@Controller
public class ApiController extends BaseController{
    private String prefix = "set/api";

    @Autowired
    ApiService apiService;

    @Autowired
    private DictService sysDictService;

    @RequiresPermissions("set:apiSet:apiSet")
    @SystemControllerLog(description="0")
    @GetMapping("")
    String merch(Model model) {
        return prefix + "/api";
    }

    @GetMapping("/list")
    @ResponseBody
    PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<ApiDo> sysUserList = apiService.list(query);

        int total = apiService.count(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        return pageUtil;
    }


    /**
     * 添加API
     *
     * @param
     * @return
     */
    @RequiresPermissions("set:apiSet:add")
    @Log("添加API")
    @GetMapping("/add")
    String add(Model model,@RequestParam Map<String, Object> params) {

        /**
         * 获取词典内的应用类型
         */
        List<DictDO> sysDictList = sysDictService.list();
        model.addAttribute("sysDictList", sysDictList);
        return prefix + "/add";
    }
    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !apiService.exit(params);
    }
        @RequiresPermissions("set:apiSet:add")
        @Log("保存API")
        @SystemControllerLog(description="1")
        @PostMapping("/save")
        @ResponseBody
        public R save(ApiDo apiDo){
            apiDo.setCreatedBy(getId());
            apiDo.setCreatedDate(new Date());
            if(apiService.save(apiDo)>0){
                return R.ok();
            }
            return R.error();
    }

    /**
     * 编辑API
     * @param
     * @return
     */
    @RequiresPermissions("set:apiSet:edit")
    @Log("编辑API")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Integer id) {
        ApiDo api = apiService.get(id);
        api.setAppType(api.getAppType().trim());
        model.addAttribute("apiDo", api);

        List<DictDO> sysDictList = sysDictService.list();
        model.addAttribute("sysDictList", sysDictList);
        return prefix+"/edit";
    }

    @RequiresPermissions("set:merchantSet:edit")
    @Log("更新商户")
    @SystemControllerLog(description="2")
    @PostMapping("/update")
    @ResponseBody
    public R update( ApiDo apiDo){
        apiDo.setUpdatedBy(getId());
        apiDo.setUpdatedDate(new Date());
        if(apiService.updateApiDoById(apiDo)>0){
            return R.ok();
        }
        return R.error();
    }


    @RequiresPermissions("set:apiSet:remove")
    @Log("删除API")
    @SystemControllerLog(description="3")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Integer id) {
        if (Constant.DEMO_ACCOUNT.equals(getUserName())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (apiService.remove(id) > 0) {
            return R.ok();
        }
        // merchService.remove(id);
        return R.error();
    }

    /**
     * 导出API
     */

    /**
     * 对商户表导出excel操作
     */

    @RequiresPermissions("set:apiSet:import")
    @Log("导出API")
    @SystemControllerLog(description="4")
    @GetMapping("/import")
    public void expExceluser(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("keyWord", request.getParameter("keyWord"));

        String fileName = "API管理.xls";

        String[] title = new String[]{"应用类型","应用ID","应用密匙"};

        List<ApiDo> list = null;

        try {
            list = apiService.list(parameterMap);

            List<Object[]> data = new ArrayList<Object[]>();
            for(int i=0; i<list.size();i++){
                Object[] str = new Object[3];
                if(null!=list.get(i).getAppType() && !list.get(i).getAppType().equals("")){
                    str[0]=list.get(i).getAppType().toString();
                }else{
                    str[0]="";
                }
                if(null!=list.get(i).getAppId()+""){
                    str[1]=list.get(i).getAppId()+"";
                }else{
                    str[1]="";
                }
                if(null!=list.get(i).getAppKey()+""){
                    str[2]=list.get(i).getAppKey()+"";
                }else{
                    str[2]="";
                }


                data.add(str);
            }
            String sheetName = "expExcel";
            byte[] b = fileName.getBytes("UTF-8");
            String formatFileName = new String(b,"ISO-8859-1");
            response.setContentType("application/ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+formatFileName);
            ExcelExportTool.exportExcel(formatFileName, sheetName, title, data, response);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            list=null;
        }
    }

}