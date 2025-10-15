 package com.example.kalps.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	@GetMapping("/home")
	public String getHomePage() {
		return "Welcome To Home Page";
	}
	@GetMapping("/dashboard")
	public String getDashboardPage() {
		return "Login Successfull";
	}
}
