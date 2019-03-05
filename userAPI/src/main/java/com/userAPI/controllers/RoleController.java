package com.userAPI.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userAPI.models.Roles;
import com.userAPI.services.RoleService;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/roles")
public class RoleController {
	
	RoleService roleService = new RoleService();
	
	// ------------------- REST API CALL TO GET ALL ROLE ---------------------------- //
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRoles(HttpServletRequest request, HttpServletResponse response) {
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
	    
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO GET A ROLE ---------------------------- //
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRole(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
	}
	
	// ------------------- REST API CALL TO CREATE A ROLE ---------------------------- //
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createRole(HttpServletRequest request, HttpServletResponse response,
			Roles role) {
		
		int newObjectID = roleService.createRole(role);
		
		response.addHeader("New Object", request.getServerName() + ":" +  request.getServerPort() + "/roles/" + newObjectID);
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		return new ResponseEntity<>("Role created", HttpStatus.CREATED);
	} 
	
	// ------------------- REST API CALL TO UPDATE AN EXISTING ROLE ---------------------------- //
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateRole(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id, Roles role) { 
		
		response.addHeader("Date", new Date().toString());
		response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires", 0);
		
		roleService.updateRole(id, role);
		return new ResponseEntity<>("Role updated", HttpStatus.OK);
	}

}
