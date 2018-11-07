package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, RedirectAttributes redirect) {
		String pesan = "";
		String pengecekan = userService.checkPassword(user.getPassword());
		if (pengecekan == "sukses") {
			userService.addUser(user);
			pesan = "User berhasil ditambahkan!";
		} else {
			pesan = "Password tidak sesuai dengan ketentuan!";
		}
		redirect.addFlashAttribute("pesan", pesan);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/updatePasswordUser")
	private String updatePasswordUser() {
		return "updatePassword";
	}
	
	@RequestMapping(value = "/updatePasswordUser", method = RequestMethod.POST)
	private String updatePasswordUserSubmit(@ModelAttribute PasswordModel password, Model model, RedirectAttributes redirect) {
		UserRoleModel user = userService.getUserDetailByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String pesan = userService.checkUpdatePassword(password, user);
		redirect.addFlashAttribute("pesan", pesan);
		return "redirect:/user/updatePasswordUser";
	}
}
