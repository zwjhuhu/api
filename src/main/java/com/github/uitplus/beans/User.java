/**
 * Copyrigt (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.beans;

import javax.validation.constraints.Size;

import com.github.kubesys.webfrk.annotations.Description;
import com.github.kubesys.webfrk.annotations.JavaBean;
import com.github.kubesys.webfrk.annotations.NotNull;

/**
 * @author wuheng
 * @since 2019.7.16
 *
 */

@JavaBean
public class User {

	@NotNull
	@Description("用户名")
	@Size(min = 4, max = 20, message = "{user.name.length.illegal}")  
	private String username;

	@Size(min = 8, max = 20)
	@NotNull
	@Description("用户密码")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return username + ":" + password;
	}

}
