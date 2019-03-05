package com.userAPI.controllers;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userAPI.models.User;
import com.userAPI.services.UserService;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/users")
public class UserController {
	
	UserService userService = new UserService();
	
	// ------------------- REST API CALL TO GET ALL USERS ---------------------------- //
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUsers(HttpServletRequest request, HttpServletResponse response) {
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
	    
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO GET AN USER ---------------------------- //
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
	    
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO VERIFY AN USER'S PASSWORD ---------------------------- //
	
	@PostMapping(value = "/verifyP/{pass}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> verifyPassword(@PathVariable("pass") String pass, HttpServletResponse response,
			Principal principal) {
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		boolean result = userService.verifyPassword(principal.getName(), pass.trim());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO LOGIN AN USER ---------------------------- //
	
	// ------------------- REST API CALL TO CREATE AN USER ---------------------------- //
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createUser(User user, HttpServletRequest request, HttpServletResponse response) {
		
		int newObjectID = userService.createUser(user);
		
		response.addHeader("New Object", request.getServerName() + ":" +  request.getServerPort() + "/users/" + newObjectID);
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		return new ResponseEntity<>("User created", HttpStatus.CREATED);
	} 
	
	// ------------------- REST API CALL TO UPDATE AN EXISTING USER ---------------------------- //
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("id") int id, User user) { 
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		userService.updateUser(id, user);
		return new ResponseEntity<>("User updated", HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO DELETE AN EXISTING USER ---------------------------- //
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteUser(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("id") int id) { 
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
	}

}
