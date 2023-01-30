package com.jwt.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.jwt.model.JwtRequest;
import com.jwt.model.Shows;
import com.jwt.model.User;

public interface LoginService {
	public ResponseEntity<?> createUser(JwtRequest user);
	public User authenticate(JwtRequest request) throws IOException;
	public User findUserByUserName(String userName);
	public List<Shows> findByPremieredDate(String premiere)throws ParseException;
	public String listOfShows(String id);
}
