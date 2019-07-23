/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.webfrk.core;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kubesys.webfrk.core.HttpFrkUtils.ValidationResult;

/**
 * @author wuheng
 * @since  2019.2.20
 * 
 * The {@code HttpController} class is used to dispatch request 
 * to the related handler, if the handler is not found, it would 
 * throw an exception. 
 */
@RestController
@ComponentScan
public final class HttpController implements ApplicationContextAware {

	/**
	 * logger 
	 */
	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**
	 * handler means how to deal with the request for 
	 * specified servletPath 
	 */
	@Autowired
	protected HandlerManager handlers;
	
	/**
	 * app context
	 */
	protected static ApplicationContext ctx;
	
	/**************************************************
	 * 
	 *      CRUD
	 * 
	 **************************************************/
	
	
	/**
	 * @param request    servlet path should be startwith 'add', 'create', or 'new'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.POST, value = {"/**/login*", "/**/add*", "/**/create*", "/**/new*"})
	public @ResponseBody String createTypeRequest(HttpServletRequest request, 
											@RequestBody  JSONObject body) throws Exception{
 		return handleHttpRequest(getServletPath(request), body);
	}
	

	/**
	 * @param request    servlet path should be startwith 'delete', or 'remove'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.DELETE}, value = {"/**/delete*", "/**/remove*"})
	public @ResponseBody String deleteTypeRequest(HttpServletRequest request, 
												@RequestBody  JSONObject body) throws Exception{
 		return handleHttpRequest(getServletPath(request), body);
	}
	
	/**
	 * @param request    servlet path should be startwith 'update', 'modify', or 'replace'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = {"/**/update*", "/**/modify*", "/**/replace*"})
	public @ResponseBody String updateTypeRequest(HttpServletRequest request, 
												@RequestBody  JSONObject body) throws Exception{
 		return handleHttpRequest(getServletPath(request), body);
	}
	
	/**
	 * @param request    servlet path should be startwith 'get', 'list', or 'describe'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.GET, value = {"/**/get*", "/**/list*", "/**/describe*", "/**/retrieve*"})
	public @ResponseBody String retrieveTypeRequest(HttpServletRequest request, 
						@RequestParam(required = false)  Map<String, Object> body) throws Exception{
 		return handleHttpRequest(getServletPath(request), new JSONObject(body));
	}
	
	/**
	 * @param request    servlet path should be startwith 'get', 'list', or 'describe'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.POST, value = {"/**/get*", "/**/list*", "/**/describe*", "/**/retrieve*"})
	public @ResponseBody String retrieveTypeRequest(HttpServletRequest request, 
			@RequestBody JSONObject body) throws Exception{
		return handleHttpRequest(getServletPath(request), body);
	}
	
	@RequestMapping(value = {"/*", "/*/*/**"})
	@ResponseBody
	public String invalidRequest(HttpServletRequest request) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
						+ " the reason is: " + HttpConstants.EXCEPTION_INVALID_REQUEST_URL);
		return HttpFrkUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED
        				, HttpConstants.EXCEPTION_INVALID_REQUEST_URL));
	}
	
	/**************************************************
	 * 
	 *      Dispatch and Exception
	 * 
	 **************************************************/
	
	/**
	 * @param servletPath
	 * @param body
	 * @return
	 * @throws Exception
	 */
	protected String handleHttpRequest(String servletPath, 
								JSONObject body) throws Exception{
		
		m_logger.info("Begin to deal with " + servletPath);
		
		try {
			// reflect based 
			Method hanlder = handlers.geHandler(servletPath);
			Object[] params = getParams(body, hanlder);
			Object result = (params != null) 
					? hanlder.invoke(getInstance(servletPath), params) 
					: hanlder.invoke(getInstance(servletPath));
			
			m_logger.info("Successfully deal with " + servletPath);
//			HttpResponse resp = new HttpResponse(HttpConstants
//					.HTTP_RESPONSE_STATUS_OK, result);
			String jsonString = JSON.toJSONString(result);
			System.err.println(jsonString);
			return jsonString;
		} catch (Exception e) {
			throw new Exception(HttpConstants.EXCEPTION_INVALID_REQUEST_URL);
		}
		
	}
	
	@ExceptionHandler
	@ResponseBody
	public String invalidRequestException(HttpServletRequest request, Exception e) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
									+ ", the reason is: " + String.valueOf(e.getMessage()));
        return HttpFrkUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED, String.valueOf(e.getMessage())));
	}
	
	
	/**************************************************
	 * 
	 *  Utils
	 * 
	 **************************************************/

	protected String getServletPath(HttpServletRequest request) {
		return request.getRequestURI().substring(
				request.getContextPath().length() + 1);
	}
	
	protected Object getInstance(String servletPath) throws Exception {
		String name = handlers.geHandler(servletPath)
								.getDeclaringClass().getSimpleName();
		return ctx.getBean(name.substring(0, 1).toLowerCase() + name.substring(1));
	}
	
	protected Object[] getParams(JSONObject body, Method targetMethod) throws Exception {
		
		// in our design , we only support two cases:
		// 1. no parameter
		// 2. only has one parameter
		
		
		if (targetMethod.getParameterCount() == 0) {
			// if there is on parameter
			return null;
		} else if (targetMethod.getParameterCount() == 1) {
			// if there is only one parameter
			Object paramObj = body.toJavaObject(targetMethod.getParameterTypes()[0]);
			Annotation[][] pas =  targetMethod.getParameterAnnotations();
			
			if (pas[0].length == 0) {
				// if no need to valid (JSR 303)
				return new Object[] {paramObj};
			} else if (pas[0].length == 1 && 
					pas[0][0].annotationType().getName()
					.equals(Valid.class.getName())) {
				// if we need to check the object because of JSR 303
				return checkWithJSR303(paramObj);
			} 
			throw new Exception("If parameter has annotation, the annotation type must be 'javax.validation.Valid'");
		} else {
			throw new Exception("Only support one parameter.");
		}
		
	}

	private Object[] checkWithJSR303(Object paramObj) throws Exception {
		ValidationResult result = HttpFrkUtils.validateEntity(paramObj);
		if (result.isHasErrors()) {
			throw new Exception(JSON.toJSONString(result.getErrorMsg()));
		}
		return new Object[] { paramObj };
	}

	@Override
	public void setApplicationContext(ApplicationContext 
						applicationContext) throws BeansException {
		if (ctx == null) {
			ctx = applicationContext;
		}
		System.out.println(ctx);
	}
	
}
