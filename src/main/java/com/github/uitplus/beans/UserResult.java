/**
 * Copyrigt (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.beans;


import com.github.kubesys.webfrk.annotations.JavaBean;

/**
 * @author wuheng
 * @since 2019.7.16
 *
 */

@JavaBean
public class UserResult {

	protected int code;
	
	protected Data data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}



	public static class Data {
		
		protected String token;
		
		protected String value;

		public Data() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

	}

}
