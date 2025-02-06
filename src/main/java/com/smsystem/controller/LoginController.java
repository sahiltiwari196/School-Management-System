package com.smsystem.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsystem.model.User;
import com.smsystem.service.AuthenticationService;
import com.smsystem.util.JwtUtil;

@RestController
@RequestMapping
public class LoginController {
	
	@Autowired
	AuthenticationService authenticationService;
	
	 @Autowired
	 private JwtUtil jwtUtil;
	
	@GetMapping("/")
	public String root() {
		return "Its Working Fine";
	}
	
	
	@GetMapping("login")
	public  ResponseEntity<Object> login( @RequestParam(required = true) String username,@RequestParam(required = true) String password) {
		try {
		User user =authenticationService.authenticateDb(username, password);
		HashMap<String, Object> resp = new HashMap<>();
		if(user!=null) {
			resp.put("Access_Token", jwtUtil.generateToken(user));
			resp.put("statusCode", 200);
		}else {
			resp.put("message","username doesnot exist");
			resp.put("statusCode", 404);
		}
		
		return ResponseEntity.ok(resp);
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body("bad request");
			
		}
	}
	

}
