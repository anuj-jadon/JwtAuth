package com.jwt.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.gson.Gson;
import com.jwt.model.JwtRequest;
import com.jwt.model.Shows;
import com.jwt.model.User;

@Service
public class LoginServiceImpl implements LoginService {
	private static String path = "C:\\credentials.txt";
	private static File file = new File(path);
	private final User user = new User();
	@Override
	public ResponseEntity<?> createUser(JwtRequest user) {
		try {

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write((user.getUsername() + ":" + user.getPassword()));
			bufferedWriter.newLine();
			bufferedWriter.close();

		} catch (IOException e) {
			return new ResponseEntity<String>("FIle Error", HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<String>("User Created", HttpStatus.OK);

	}

	@Override
	public User authenticate(JwtRequest request) throws IOException {
		
		if (!file.exists()) {
			throw new IOException();
		} else {
			Map<String, String> mapFromFile = HashMapFromTextFile();

			for (Map.Entry<String, String> entry : mapFromFile.entrySet()) {
				if(entry.getKey().equals(request.getUsername()) && entry.getValue().equals(request.getPassword())) {
					user.setUsername(request.getUsername());
					user.setPassword(request.getPassword());
					
				}
			}
		}
		if(user==null) {
			throw new UsernameNotFoundException("No user found with "+user.getUsername()+" name");
		}
		return user;
	}
	
	@Override
	public User findUserByUserName(String userName) {
		return user;
	}

	public static Map<String, String> HashMapFromTextFile() {

		Map<String, String> map = new HashMap<String, String>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));

			String line = null;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				String name = parts[0].trim();
				String number = parts[1].trim();
				if (!name.equals("") && !number.equals(""))
					map.put(name, number);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
				;
			}
		}

		return map;
	}

	@Override
	public List<Shows> findByPremieredDate(String premiere) throws ParseException {
		List<Shows> shows = new ArrayList<Shows>();
		
		System.out.println(premiere);
		String uri = "http://api.tvmaze.com/shows";
		RestTemplate restTemplate = new RestTemplate();
		JsonObject[] obj = restTemplate.getForObject(uri, JsonObject[].class);

		for (JsonObject ob : obj) {
			
			String date2 =(String)ob.get("premiered");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date firstDate = sdf.parse(premiere);
			Date secondDate = sdf.parse(date2);
		    long diffInMillies = secondDate.getTime() - firstDate.getTime();

			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
			if (diff > 0) {
				Shows show  = new Shows();
				show.setId((int) ob.get("id"));
				show.setName((String)ob.get("name"));
				shows.add(show);
			}

		}
		return shows;
	}

	@Override
	public String listOfShows(String id) {
		String uri = "http://api.tvmaze.com/shows/"+id;

		Gson gson = new Gson();

		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		JsonObject obj = restTemplate.getForObject(uri, JsonObject.class);
		
		String[] str = {"genres", "averageRuntime","ended","officialSite","schedule",
				"rating","weight","network","webChannel","dvdCountry","image","_links"};
		
		for(String st : str) {
			obj.remove(st);
		}
		String[] str2 = {"airtime","airstamp","runtime","rating","summary","_links"};
		String uri2 = "http://api.tvmaze.com/shows/"+id+"/episodes";
		RestTemplate restTemplate2 = new RestTemplate();
		JsonObject[] obj2 = restTemplate2.getForObject(uri2, JsonObject[].class);
		for(JsonObject object : obj2) {
			for(String string:str2) {
				object.remove(string);
			}
		}
		obj.put("episodes", obj2);
		String objString = gson.toJson(obj);

		return objString;
	}



}