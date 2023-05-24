package com.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.authorization.filter.JwtFilter;

@SpringBootApplication
public class AuthorizationApplication {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean jwtFilter()
	{
	 FilterRegistrationBean fb = new FilterRegistrationBean();
	 fb.setFilter(new JwtFilter());
	 fb.addUrlPatterns("/api/v1.0/moviebooking/*");
	 return fb;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
