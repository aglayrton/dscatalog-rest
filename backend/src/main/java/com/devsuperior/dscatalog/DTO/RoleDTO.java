package com.devsuperior.dscatalog.DTO;

import java.io.Serializable;

public class RoleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String authority;
	
	public RoleDTO() {

	}
	
	public RoleDTO(long id, String authority) {
		this.id = id;
		this.authority = authority;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
}
