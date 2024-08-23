package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDApplication.*;
import com.example.repo.UserRepo;

@RestController
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		try {
			List<User> userList = new ArrayList<>();
			userRepo.findAll().forEach(userList::add);
			if (userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(userList,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/getUserByUserId/{id}")
	public ResponseEntity<User> getUserByUserName(@PathVariable Long id) {
		Optional<User> userData=userRepo.findById(id);
		if(userData.isPresent()) {
			return new ResponseEntity<>(userData.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User userObj= userRepo.save(user)	;
		 return new ResponseEntity<>(userObj,HttpStatus.OK);
	}
	
	@PostMapping("/updateUserByUserId/{id}")
	public ResponseEntity<User> updateUserByUserName(@PathVariable Long id, @RequestBody User newUserData) {
		Optional<User> oldUserData = userRepo.findById(id);
		if (oldUserData.isPresent()) {
			User updateUserData = oldUserData.get();
			updateUserData.setActive(newUserData.isActive());
			updateUserData.setPassword(newUserData.getPassword());
			User userObj = userRepo.save(updateUserData);
			return new ResponseEntity<>(userObj, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteUserByUserId/{id}")
	public ResponseEntity<Object> deleteUserByUserName(@PathVariable Long id) {
		userRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
