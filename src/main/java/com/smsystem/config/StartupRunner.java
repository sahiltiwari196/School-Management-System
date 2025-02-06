package com.smsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smsystem.model.Admin;
import com.smsystem.repository.UserRepository;
import com.smsystem.util.PasswordUtil;

@Component
public class StartupRunner implements CommandLineRunner {

	  @Autowired
	    UserRepository userRepository;

//    public StartupRunner(AdminService adminService) {
//        this.adminService = adminService;
//    }

    @Override
    public void run(String... args) throws Exception {
    	String encodedPassword = PasswordUtil.encodePassword("admin123");

  
        Admin admin = new Admin();
        admin.setUsername("admin_username");
        admin.setPassword(encodedPassword);

   
        userRepository.save(admin);
        System.out.println("Admin user created successfully!");
    }
}
