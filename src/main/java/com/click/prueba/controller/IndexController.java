package com.click.prueba.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.click.prueba.model.Users;
import com.click.prueba.service.JustClickService;

@Controller
public class IndexController {


	@Autowired
	private JustClickService userService;

	@GetMapping("/link/{username}")
	public String home(@PathVariable String username, Model model, @CookieValue(
			value = "currentValue", defaultValue = "0") Integer currentValue,
			@CookieValue(
					value = "userInfo", defaultValue = "") String userInfo,
			@RequestHeader HttpHeaders headersValues,
			HttpServletResponse response) {
		Cookie currentValueCookie;
		Cookie userCookie = null;
		Users user = userService.findByUsername(username);
		userService.loggerRequest(headersValues);
		if(user != null) {
			currentValue++;
			currentValueCookie = new Cookie("currentValue", Integer.toString(currentValue));
			response.addCookie(currentValueCookie);
			if(userInfo.isEmpty()) {
				userCookie = new Cookie("userInfo", user.getUsername());
				response.addCookie(userCookie);
			}
			if (currentValue < user.getMaxValue()) {
				model.addAttribute("user", user);
				return("index");
			} else {
				return("404");
			}

		} else {
			return("404");
		}

	}
	
}
