package com.passuol.sp.challenge03.msuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScans({ @ComponentScan("com.passuol.sp.challenge03.msuser.controller"), @ComponentScan("com.passuol.sp.challenge03.msuser.config")})
//@EnableJpaRepositories("com.passuol.sp.challenge03.msuser.repository")
//@EntityScan("com.passuol.sp.challenge03.msuser.model")
public class MsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserApplication.class, args);
	}

}
