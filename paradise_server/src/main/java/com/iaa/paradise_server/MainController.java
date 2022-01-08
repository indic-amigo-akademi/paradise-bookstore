package com.iaa.paradise_server;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

	@PostMapping("/saveUser")
	public String saveUser(@RequestBody User usr){
		System.out.println(usr.toString());
		usrdao.create(usr);
		return "User saved successfully";

	}
	@GetMapping("/getUsers")
	@ResponseBody
	public String getUser(User usr){
		Map<String,User> m=usrdao.getAll();
		String str="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		List<> m2=m.values().stream().map(s-> s.getUserName()).collect(Collectors.toList());
		for(User key:m.values()){
			str+=key.toString();
		}
		System.out.println(str);
		return str;

	}
	@RequestMapping("/login")
	public ModelAndView loginPage()
	{
		return new ModelAndView("login");
	}
	@RequestMapping("/registration")
	public ModelAndView register()
	{
		return new ModelAndView("register");
	}
	@GetMapping("/home")
	public String home() {
		return "index";
	}


}