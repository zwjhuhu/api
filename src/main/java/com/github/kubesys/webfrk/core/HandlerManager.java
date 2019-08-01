/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.kubesys.webfrk.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class <code>HandlerManager<code> is used for registering various service.
 * 
 * @author wuheng
 * @since  2019.2.20
 */
@Component
public final class HandlerManager  {

	/**
	 * servlet handlers
	 */
	protected static Map<String, Method> servletHandlers = new HashMap<String, Method>();

	/**
	 * service types, the key are CURD, the values are the prefixs for each type (CURD)
	 */
	protected static Map<String, List<String>> serviceTypes = new HashMap<String, List<String>>();
	
	/**
	 * black list
	 */
	protected static Set<String> blacklist = new HashSet<String>();
	
	static {
		blacklist.add("getClass");
	}
	
	/**
	 * @throws Exception
	 */
	public HandlerManager() throws Exception {
		
		// service types include create, update, retrieve and delete.
		// we can get the valid request name for each service type
		// by analysing the annotations of the related methods
		// in class <code>HttpController<code>
		Class<?> clazz = Class.forName(HttpController.class.getName());
		
		for (Method method : clazz.getMethods()) {
			
			RequestMapping hasAnnotation = method.getAnnotation(RequestMapping.class);
			
			// only the method has 'RequestMapping' annotation and 
			// the method name starts with 'invalid' is the serviceType 
			// related method in  class <code>HttpController<code>
			if (hasAnnotation != null && !method.getName().startsWith("invalid")) {
				
				String serType = HttpFrkUtils.getServiceType(method);
				List<String> list = getServicePrefixList(serType);
				for (String value : hasAnnotation.value()) {
					int idx = value.lastIndexOf("/");
					String prefix = value.substring(idx + 1, value.length() - 1);
					list.add(prefix);
				}
			} 
		}
	}

	/**
	 * @param serType   service type
	 * @return          prefix list
	 */
	private List<String> getServicePrefixList(String serType) {
		List<String> list = null;
		if (serviceTypes.containsKey(serType)) {
			list = serviceTypes.get(serType);
		} else {
			list = new ArrayList<String>();
			serviceTypes.put(serType, list);
		}
		return list;
	}

	/**
	 * @param serviceModule   service module
	 * @param serviceName     service name
	 * @return                true if no exception
	 * @throws Exception      exception
	 */
	public boolean addHandler(String serviceModule, Method serviceName) throws Exception {
		
		if (isValidRequestByAnalysingPrefixs(serviceName)) {
			servletHandlers.put(serviceModule + 
					"/" + serviceName.getName(), serviceName);
			return true;
		} 
		return false;
	}

	/**
	 * @param servletPath   servlet
	 * @return              the related method
	 * @throws Exception    exception
	 */
	public Method geHandler(String servletPath) throws Exception {
		return servletHandlers.get(servletPath);
	}

	
	/**
	 * @param method      method
	 * @return            true if it is a valid request
	 * @throws Exception  exception 
	 */
	private static boolean isValidRequestByAnalysingPrefixs(Method method) throws Exception {
		
		// ignore the method name in 'blacklist' 
		if (blacklist.contains(method.getName())) {
			return false;
		}
		
		if (isValidRequest(method)) {
			return true;
		} else {
			return false;
		}
		
	}

	private static boolean isValidRequest(Method method) throws Exception {
		List<String> durlist = new ArrayList<String>();
		durlist.addAll(serviceTypes.get("deleteType"));
		durlist.addAll(serviceTypes.get("updateType"));
		durlist.addAll(serviceTypes.get("createType"));
		durlist.addAll(serviceTypes.get("retrieveType"));
		for (String v : durlist) {
			// it is a create, update, or delete request
			if (method.getName().startsWith(v)){
				return true;
			}
		}
		
		return false;
	}
	
//	private static boolean isValidCreateOrUpdateOrDeleteRequest(Method method) throws Exception {
//		List<String> durlist = new ArrayList<String>();
//		durlist.addAll(serviceTypes.get("deleteType"));
//		durlist.addAll(serviceTypes.get("updateType"));
//		durlist.addAll(serviceTypes.get("createType"));
//		for (String v : durlist) {
//			// it is a create, update, or delete request
//			if (method.getName().startsWith(v)){
//				// if the parameter count is 1
//				// it is a valid request
//				if (method.getParameterCount() == 1 ) {
//					return true;
//				} else {
//					throw new Exception(method.getName() + 
//							": Only support one parameter, and the "
//							+ "parameter class cannot be start with 'java.lang'.");
//				}
//			}
//		}
//		
//		return false;
//	}
//
//	private static boolean isValidRetrieveRequest(Method method) throws Exception {
//		List<String> clist = serviceTypes.get("retrieveType");
//		for (String v : clist) {
//			// it is a retrieve request
//			if (method.getName().startsWith(v)) {
//				// if the parameter count is 0, or if the parameter is a Map object
//				// it is a valid request
//				if ((method.getParameterCount() == 0) 
//						|| (method.getParameterCount() == 1 
//						   && method.getParameterTypes()[0].getAnnotations().length == 0
//							&& method.getParameterTypes()[0].getName().equals(Map.class.getName()))) {
//					return true;
//				} else {
//					throw new Exception(method.getName() + 
//							": Support no prameter or only a Map object");
//				}
//			} 
//		}
//		return false;
//	}
		
}
