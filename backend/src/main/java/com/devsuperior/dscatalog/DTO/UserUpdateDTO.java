package com.devsuperior.dscatalog.DTO;

import com.devsuperior.dscatalog.service.validation.UserUpdateValid;

@UserUpdateValid //essa annotation processa a verificação do email pra ve se ja existe no email
public class UserUpdateDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
