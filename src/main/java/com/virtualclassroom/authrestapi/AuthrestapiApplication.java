package com.virtualclassroom.authrestapi;

import com.virtualclassroom.authrestapi.entities.Role;
import com.virtualclassroom.authrestapi.entities.User;
import com.virtualclassroom.authrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthrestapiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(AuthrestapiApplication.class, args);
	}


	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (adminAccount == null){
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setSecondname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);

		}
	}
}
