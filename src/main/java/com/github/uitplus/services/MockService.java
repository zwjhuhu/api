/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.services;

import java.util.Map;

import com.github.kubesys.webfrk.annotations.ServiceDefinition;
import com.github.kubesys.webfrk.annotations.handlers.HttpBodyHandler;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
@ServiceDefinition
public class MockService extends HttpBodyHandler {

	public boolean list(String name, Map<String, String> value) {
		return true;
	}
}
