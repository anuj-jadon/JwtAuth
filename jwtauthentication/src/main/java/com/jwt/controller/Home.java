package com.jwt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.websocket.server.PathParam;

import org.hibernate.sql.Delete;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.gson.Gson;
import com.jwt.model.JwtRequest;
import com.jwt.model.Shows;
import com.jwt.services.LoginService;

@RestController
@CrossOrigin(origins = "*")
public class Home {

	@Autowired
	private LoginService loginservice;

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestBody JwtRequest user) {
		ResponseEntity<?> response = loginservice.createUser(user);
		return response;
	}


	@GetMapping("/show-info")
	public String getListOfShows(@RequestParam String show_id) {

		String objString = loginservice.listOfShows(show_id);

		return objString;
	}

	@GetMapping("/filter-shows")
	public List<Shows> getListOfShowsOnPremieredDate(@RequestParam String premiere) throws ParseException {
		List<Shows> shows = loginservice.findByPremieredDate(premiere);
		return shows;
	}

}
