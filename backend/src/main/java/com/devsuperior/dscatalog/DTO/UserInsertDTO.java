package com.devsuperior.dscatalog.DTO;

import com.devsuperior.dscatalog.service.validation.UserInsertValid;

@UserInsertValid //essa annotation processa a verificação do email pra ve se ja existe no email
public class UserInsertDTO extends UserDTO {

	private static final long serialVersionUID = 1L;
	private String password;
	

	public UserInsertDTO() {
		super();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
