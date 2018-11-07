package com.apap.tutorial8.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public String checkPassword(String password) {
		String pesan = "";
		if (password.length()>7 && Pattern.compile("[0-9]").matcher(password).find() && Pattern.compile("[a-zA-Z]").matcher(password).find()) {
			pesan = "sukses";
		} else {
			pesan = "gagal";
		}
		
//		for (int i = 0; i<password.length(); i++) {
//			if (Character.isLetterOrDigit(password.charAt(i)) && password.length()) {
//				pesan = "sukses";
//			} else {
//				pesan = "gagal";
//			}
//		}
		return pesan;
	}

	@Override
	public UserRoleModel getUserDetailByUsername(String username) {
		return userDb.findByUsername(username);
	}

	@Override
	public String checkUpdatePassword(PasswordModel password, UserRoleModel user) {
		String pesan = "";
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		
		if (password.getPasswordBaru().equals(password.getPasswordBaruKonfirmasi())) {
			if (passEncoder.matches(password.getPasswordLama(), user.getPassword())) {
				if (checkPassword(password.getPasswordBaru()) == "sukses") {
					gantiPassword(user, password.getPasswordBaru());
					pesan = "Password berhasil diubah!";
				} else {
					pesan = "Password tidak sesuai dengan ketentuan!";
				}
			} else {
				pesan = "Password lama yang dimasukkan salah!";
			}
		} else {
			pesan = "Password yang diubah harus sama!";
		}
		return pesan;
	}

	@Override
	public void gantiPassword(UserRoleModel user, String passwordBaru) {
		String password = encrypt(passwordBaru);
		user.setPassword(password);
		userDb.save(user);
	}
}
