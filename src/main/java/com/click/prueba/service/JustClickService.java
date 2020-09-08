package com.click.prueba.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.click.prueba.model.UserAgent;
import com.click.prueba.model.Users;
import com.click.prueba.repository.UserAgentRepository;
import com.click.prueba.repository.UsersRepository;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

@Service
public class JustClickService {
  
	    
	public static final String FILE ="C:\\Users\\Hitss\\Documents\\workspace-spring-tool-suite-4-4.4.0.RELEASE\\pruebaClick\\src\\main\\resources\\pruebaClick.json";
		@Autowired 
		private UsersRepository repository;
		
		@Autowired 
		private UserAgentRepository AgentRepository;
		
		
		
		public Users findByUsername(String username) {
			return repository.findByUsername(username);
		}
		
		
		public void loggerRequest(HttpHeaders headers) {
			Gson gson = new Gson();
			try {
				FileWriter writer = new FileWriter(FILE, true);
				gson.toJson(headers, writer);
				writer.write("\n");
				writer.flush();
				writer.close();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		
		@Scheduled(fixedDelay = 3900000, initialDelay = 3900000)
		public void program() {
			Gson gson = new Gson();
			try {
			System.out.println("se ejecuto tareaprogramada");
				AgentRepository.deleteAll();
				@SuppressWarnings("resource")
				Stream<String> lines = Files.lines(Paths.get(FILE));
				lines.forEach(line -> {
					UserAgent userAgent = new UserAgent();
					HttpHeaders header = gson.fromJson(line, HttpHeaders.class);
					userAgent = this.getUserAgent(header.get("user-agent").get(0));
					AgentRepository.save(userAgent);
					System.out.println("se ejecuto tareaprogramada" + userAgent.toString());
				});	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}

		public UserAgent getUserAgent(String userAgent) {
			String osType = "Unknown";
			String osVersion = "Unknown";
			String browserType = "Unknown";
			String browserVersion = "Unknown";
			String deviceType = "Unknown";
			UserAgent userAgentNew = new UserAgent();
			try {
				if (userAgent.indexOf("Windows NT") >= 0) {
					osType = "Windows";
					osVersion = userAgent.substring(userAgent.indexOf("Windows NT ")+11, userAgent.indexOf(";"));

				} else if (userAgent.indexOf("Mac OS") >= 0) {
					osType = "Mac";
					osVersion = userAgent.substring(userAgent.indexOf("Mac OS ")+7, userAgent.indexOf(")"));

					if(userAgent.indexOf("iPhone") >= 0) {
						deviceType = "iPhone";
					} else if(userAgent.indexOf("iPad") >= 0) {
						deviceType = "iPad";
					}

				} else if (userAgent.indexOf("X11") >= 0) {
					osType = "Unix";
					osVersion = "Unknown";

				} 
				
				if (userAgent.contains("Edge/")) {
					browserType = "Edge";
					browserVersion = userAgent.substring(userAgent.indexOf("Edge")).split("/")[1];

				} else if (userAgent.contains("Safari/") && userAgent.contains("Version/")) {
					browserType = "Safari";
					browserVersion = userAgent.substring(userAgent.indexOf("Version/")+8).split(" ")[0];

				} else if (userAgent.contains("OPR/") || userAgent.contains("Opera/")) {
					browserType = "Opera";
					browserVersion = userAgent.substring(userAgent.indexOf("OPR")).split("/")[1];

				} else if (userAgent.contains("Chrome/")) {
					browserType = "Chrome"; 
					browserVersion = userAgent.substring(userAgent.indexOf("Chrome")).split("/")[1];
					browserVersion = browserVersion.split(" ")[0];

				} else if (userAgent.contains("Firefox/")) {
					browserType = "Firefox"; 
					browserVersion = userAgent.substring(userAgent.indexOf("Firefox")).split("/")[1];
				} 
				
				userAgentNew.setOsType(osType);
				userAgentNew.setOsVersion(osVersion);
				userAgentNew.setBrowserType(browserType);
				userAgentNew.setBrowserVersion(browserVersion);
				userAgentNew.setDeviceType(deviceType);
			} catch (Exception e) {
				
			}
			
			return userAgentNew;
		}

}