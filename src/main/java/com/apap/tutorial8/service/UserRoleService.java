package com.apap.tutorial8.service;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	String checkPassword(String password);
	String checkUpdatePassword(PasswordModel password, UserRoleModel user);
	void gantiPassword(UserRoleModel user, String passwordBaru);
	UserRoleModel getUserDetailByUsername(String username);
}
