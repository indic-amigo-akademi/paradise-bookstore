package com.iaa.paradise_server.Controllers;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
	@Autowired
	UserRepository usrdao;
	@GetMapping(value="/",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() {
		return "Hello from spring boot";
	}

	@GetMapping("/auth/register")
	public ModelAndView register(){
		return new ModelAndView("register");
	}

	@PostMapping(value = "/auth/register", consumes = {"application/x-www-form-urlencoded"})
	public String saveUser(User usr){
		System.out.println(usr.toString());
		usr.setPassword(new BCryptPasswordEncoder().encode(usr.getPassword()));
		usrdao.save(usr);
		System.out.println("User saved successfully");
		return "redirect:/auth/login";

	}
	@GetMapping("/getUsers")
	@ResponseBody
	public String getUser(User usr){
		List<User> m=usrdao.findAll();
		String str=m.toString();
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		List<> m2=m.values().stream().map(s-> s.getUserName()).collect(Collectors.toList());
		System.out.println(str);
		return str;

	}
	@GetMapping("/auth/login")
	public ModelAndView loginPage()
	{
		return new ModelAndView("login");
	}

	@GetMapping("/auth/verify")
	public String verify()
	{
		String user = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		return user;

	}

	@GetMapping("/home")
	public String home()
	{
		String user = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		return user;
	}


}