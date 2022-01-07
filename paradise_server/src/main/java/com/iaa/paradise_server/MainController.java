package com.iaa.paradise_server;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MainController {
	@Autowired
	UserRepository usrdao;
	@GetMapping(value="/",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() {
		return "Hello from spring boot";
	}

	@PostMapping("/saveUser")
	public void saveUser(@RequestBody User usr){
		System.out.println(usr.toString());
		usrdao.create(usr);
		System.out.println("User saved successfully");
	}
	@GetMapping("/getUsers")
	public String getUser(User usr){
		Map<String,User> m=usrdao.getAll();
		String str="";
		for (User key: m.values()) {
			str+=key.toString();
			System.out.println(key);
		}
		return str;
	}

}