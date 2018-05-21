package com.slzr.common.aop;


import com.alibaba.fastjson.JSON;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.domain.LogContentDo;
import com.slzr.common.domain.LogDO;
import com.slzr.common.service.LogService;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.JSONUtils;
import com.slzr.common.utils.ShiroUtils;
import com.slzr.common.utils.UuidUtils;
import com.slzr.system.domain.UserDO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
*
 * 系统日志切点类
 * @author linrx
 *

*/

@Aspect
@Component
public class SystemLogAspect {
	private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);

	private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
	private static final ThreadLocal<LogDO> logThreadLocal =  new NamedThreadLocal<LogDO>("ThreadLocal log");

    private static final ThreadLocal<LogContentDo> logContentLocal =  new NamedThreadLocal<LogContentDo>("ThreadLocal logContent");

	private static final ThreadLocal<UserDO> currentUser=new NamedThreadLocal<>("ThreadLocal user");
    String moduleID="";

	@Autowired(required=false)
	private HttpServletRequest request;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private LogService logService;

/*Service层切点*/


/*	@Pointcut("@annotation(com.myron.ims.annotation.SystemServiceLog)")
	public void serviceAspect(){}*/

/*
*/
	/*Controller层切点 注解拦截*/


	@Pointcut("execution(* com.slzr.*.controller.*.*(..)) && @annotation(com.slzr.common.annotation.SystemControllerLog)")
	public void controllerAspect(){}
	
/*
*
	 * 方法规则拦截

*/

	@Pointcut("execution(* com.slzr.*.controller.*.*(..))")
	public void controllerPointerCut(){}
/**
	 * 前置通知 用于拦截Controller层记录用户的操作的开始时间
	 * @param joinPoint 切点
	 * @throws InterruptedException 
	 */

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws InterruptedException{
		Date beginTime=new Date();
		beginTimeThreadLocal.set(beginTime);
		//debug模式下 显式打印开始时间用于调试
		/*if (logger.isDebugEnabled()){
	        logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
	        	.format(beginTime), request.getRequestURI());
			Object [] args = joinPoint.getArgs();
			System.out.println(args);
		}*/
		//读取session中的用户
		HttpSession session = request.getSession();
		//UserDO user1 = getUser();
		UserDO user = (UserDO) session.getAttribute("ims_user");
		currentUser.set(user);

	}
	
/**
	 * 环绕通知 用于拦截Controller层记录用户的操作
	 * @param joinPoint 切点
	 */

	@SuppressWarnings("unchecked")
	@Around("controllerAspect()")
	public Object doAfter(ProceedingJoinPoint joinPoint)throws  Throwable {
		UserDO user = ShiroUtils.getUser();

		Object proceed = joinPoint.proceed();
		createMethod(joinPoint);

		return  proceed;
	}
	
/**
	 *  异常通知 
	 * @param joinPoint
	 * @param e
	 */

	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		LogDO log = logThreadLocal.get();
        LogContentDo logContentDo = logContentLocal.get();
        if(log != null){
			log.setLogTypeID(3);//代表操作失败

            logContentDo.setErrMeaaager(e.toString());
            String seeting = JSONUtils.beanToJson(logContentDo);
            log.setLogContent(seeting);
            new UpdateLogThread(log, logService).start();
		}
	}

/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint 切点
	 * @return 方法描述
	 */

	public static String getControllerMethodDescription2(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SystemControllerLog controllerLog = method
				.getAnnotation(SystemControllerLog.class);
		String discription = controllerLog.description();
		return discription;
	}

/**
	 * 保存日志线程
	 * 
	 * @author lin.r.x
	 *
	 */

	private static class SaveLogThread implements Runnable {
		private LogDO log;
		private LogService logService;

		public SaveLogThread(LogDO log, LogService logService) {
			this.log = log;
			this.logService = logService;
		}

		@Override
		public void run() {
			logService.createLog(log);
		}
	}

/**
	 * 日志更新线程
	 * 
	 * @author lin.r.x
	 *
	 */

	private static class UpdateLogThread extends Thread {
		private LogDO log;
		private LogService logService;

		public UpdateLogThread(LogDO log, LogService logService) {
			super(UpdateLogThread.class.getSimpleName());
			this.log = log;
			this.logService = logService;
		}

		@Override
		public void run() {
			this.logService.updateLog(log);
		}
	}


	public void createMethod(ProceedingJoinPoint joinPoint){
		UserDO user = ShiroUtils.getUser();
		if(user == null){
			HttpSession session = request.getSession();
			if(user==null){
				return ;
			}
		}
		/**
		 * 获取类名及其方法名
		 */
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		String className= method.getDeclaringClass().getName();//类名
		String methodName = method.getName();//方法名
		StringBuffer sb=new StringBuffer();
		StringBuffer append = sb.append("classname:" ).append(className).append(" ：methodName:" ).append(methodName);

		String title="";
		//Integer logType=2; 					 //日志类型(2:入库,1:错误)
		//Integer type=1;						  //操作类型ID
		String remoteAddr=request.getRemoteAddr();//请求的IP
		String requestUri=request.getRequestURI();//请求的Uri
		String queryString = request.getQueryString();//URl带的参数
		if(queryString!=null&&queryString.contains("moduleID")&&!queryString.equals("")) {
			String[] query = queryString.split("=");
			moduleID=query[1];
		}
		moduleID=(moduleID==null?moduleID:moduleID);

		//String method=request.getMethod();		  //请求的方法类型(post/get)
		Map<String,String[]> param=request.getParameterMap(); //请求提交的参数
		Map<String,String> newParam= new HashMap<>();
		for(String key :param.keySet()){
			String[] strings = param.get(key);
			for(int i=0;i<strings.length;i++){
				newParam.put(key,strings[i]);
			}
		}
		String params = JSON.toJSONString(newParam);


		try {
			title=getControllerMethodDescription2(joinPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(title);
		//logger.debug(title);

		// debu模式下打印JVM信息。
		LogDO log=new LogDO();
		//log.setId(UuidUtils.creatUUID());
		log.setUserId(user.getId());
		log.setOperateTypeID(Integer.parseInt(title));//操作类型ID
		if(Integer.parseInt(title)==16||Integer.parseInt(title)==17){
			log.setModuleID(1);
		}else {
			if(title.equals("7")){
				log.setModuleID(34);
			}else {
				log.setModuleID(Integer.parseInt(moduleID));//模块ID
			}
		}
		log.setLogTypeID(1);//日志类型ID
		LogContentDo logContentDo=new LogContentDo();

		if(!title.equals("16")&&!title.equals("0")&&!title.equals("4")&&!title.equals("7")){
			//logContentDo.setId(UuidUtils.creatUUID());
			logContentDo.setParams(params);
			logContentLocal.set(logContentDo);
			String logContent = JSONUtils.beanToJson(logContentDo);
			log.setLogContent(logContent);//日志内容
		}else{
			String logContent = JSONUtils.beanToJson(logContentDo);
			//logContentDo.setId(UuidUtils.creatUUID());
			log.setLogContent(logContent);//日志内容
		}
		log.setLogDateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));//用户操作的时间
		log.setIp(remoteAddr);//用户登录的IP地址
		log.setSourceMethod(append.toString());//日志来源的方法名称

		//1.直接执行保存操作
		//this.logService.createSystemLog(log);

		//2.优化:异步保存日志
		//new SaveLogThread(log, logService).start();

		//3.再优化:通过线程池来执行日志保存
		threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
		logThreadLocal.set(log);

	}
}
