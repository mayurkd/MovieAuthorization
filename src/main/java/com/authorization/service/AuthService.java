package com.authorization.service;

import com.authorization.model.Movie;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public String generateToken(String username, String password) throws ServletException, Exception {
        String jwtToken;
        if (username == null || password == null) {
            throw new ServletException("Please enter valid credentials");
        }

        boolean flag = userService.loginUser(username, password);

        if (!flag) {
            throw new ServletException("Invalid credentials");
        } else {
            jwtToken = Jwts.builder().setSubject(username).setAudience("USER").setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 600000))
                    .signWith(SignatureAlgorithm.HS256, "secret key").compact();

        }
        return jwtToken;

    }
    @KafkaListener(topics = {"movie-app"})
    public void consumeMovieData(Movie movie){
        System.out.printf("New Released Movie Details %n Movie Id: %d %n Movie Name: %s %n Total Tickets %s %n Theater Name: %s %n",movie.getMovieId(), movie.getMovieName(),movie.getTotalTickets(),movie.getTheaterName());


    }

}