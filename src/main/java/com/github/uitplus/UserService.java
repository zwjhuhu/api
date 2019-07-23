/**
 * Copyrigt (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus;

import javax.validation.Valid;

import com.github.kubesys.webfrk.annotations.ServiceDefinition;
import com.github.kubesys.webfrk.annotations.handlers.HttpBodyHandler;
import com.github.uitplus.beans.User;
import com.github.uitplus.beans.UserResult;
import com.github.uitplus.beans.UserResult.Data;

/**
 * @author wuheng
 * @since 2019.7.16
 *
 */

@ServiceDefinition
public class UserService extends HttpBodyHandler {

	public UserResult login(@Valid User user) {
		System.err.println(user);
		UserResult res = new UserResult();
		res.setCode(20000);
		if (user.getUsername().equals("admin")) {
			Data data = new Data();
			data.setToken("admin-token");
			res.setData(data);
		} else {
			Data data = new Data();
			data.setToken("ladder-token");
			res.setData(data);
		}
		return res;
	}
}
