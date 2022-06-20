package com.ltd.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ltd.boundaries.CustomerBoundary;
import com.ltd.boundaries.UserBoundary;
import com.ltd.logic.UserService;

@Controller
public class UserController {
	private UserService userService;
	private CustomerController customerController;

	@Autowired
	public void setCustomerController(CustomerController customerController) {
		this.customerController = customerController;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/forgot_password_form")
	public String forgotPass(Model model) {
		model.addAttribute("user", new UserBoundary());
		return "forgot_password_form";
	}

	@PostMapping("/mailSent")
	public String sendKeyToEmail(@ModelAttribute("user") UserBoundary newUser, Model model, HttpSession session) {
		userService.sendKeyToEmail(newUser.getEmail());
		session.setAttribute("email", newUser.getEmail());
		return mailSent(newUser, model, session);
	}

	@GetMapping("/mail_sent_form")
	public String mailSent(@ModelAttribute("user") UserBoundary newUser, Model model, HttpSession session) {
		return "mail_sent_form";
	}

	@PostMapping("/checkKeyMatch")
	public String checkKeyMatch(@ModelAttribute("user") UserBoundary newUser, Model model, HttpSession session) {
		String email = (String) session.getAttribute("email");
		session.setAttribute("otp", newUser.getOptionalPassword());

		try {
			userService.checkKeyMatch(email, newUser.getOptionalPassword());
		} catch (Exception e) {
			e.printStackTrace();
			return "mail_sent_form";
		}
		return "new_password_form";
	}

	@GetMapping("/new_password_form")
	public String newPassword(Model model) {
		model.addAttribute("user", new UserBoundary());
		return "new_password_form";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute UserBoundary newDetails, Model model, HttpSession session) {
		String email = (String) session.getAttribute("email");
		String otp = (String) session.getAttribute("otp");
		userService.resetPassword(email, newDetails.getPassword(), otp);
		return "login";
	}

	// Users knows his password
	@GetMapping("/change_password_form")
	public String changePass(Model model) {
		model.addAttribute("user", new UserBoundary());
		return "change_password_form";
	}

	@GetMapping("/register_form")
	public String signup(Model model) {
		model.addAttribute("user", new UserBoundary());
		return "register_form";
	}

	@PostMapping("/signup")
	public String register(@ModelAttribute("user") UserBoundary newUser) {
		userService.register(newUser);
		return "login";
	}

	@PostMapping("/changePassword")
	public String updateUser(@ModelAttribute("user") UserBoundary newDetails, Model model) {
		userService.updateUser(newDetails);
		return customerController.users(new CustomerBoundary(), model);
	}

}
