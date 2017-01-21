package com.niit.backend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.backend.dao.FileUploadDao;
import com.niit.backend.dao.UserDao;
import com.niit.backend.model.User;
import com.niit.backend.model.Error;
import com.niit.backend.model.UploadFile;

@RestController
public class UserController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;
	@Autowired
	private FileUploadDao fileUploadDao;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?>login(@RequestBody User user,HttpSession session){
		logger.debug("Entering USERCONTROLLER:LOGIN");
		logger.debug("USERNAME:"+user.getUsername()+"PASSWORD"+user.getPassword());
		User validUser=userDao.authenticate(user);
		if(validUser==null){
			logger.debug("ValidUser is null");
			Error error=new Error(1,"Invalid credentials!!");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else{
			
			session.setAttribute("user", validUser);
			validUser.setOnline(true);
			userDao.updateUser(validUser);
			logger.debug("validUser is not null!!");
			
			UploadFile getUploadFile=fileUploadDao.getFile(user.getUsername());
			if(getUploadFile!=null){
				String name=getUploadFile.getFileName();
				System.out.println(getUploadFile.getData());
				byte[] imagefiles=getUploadFile.getData();
				try{
					String path="D:/instance/proj2backend/src/main/webapp/WEB-INF/resources/images/" +user.getUsername();
					File file=new File(path);
					FileOutputStream fos=new FileOutputStream(file);
					fos.write(imagefiles);
					fos.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?>registerUser(@RequestBody User user){
		try{
			logger.debug("USERCONTROLLER=>REGISTER" + user);
			user.setStatus(true);
			user.setOnline(false);
			User savedUser=userDao.registerUser(user);
			logger.debug("User Id generated is", + savedUser.getId());
			if(savedUser.getId()==0){
				Error error=new Error(2,"couldn't insert user details");
				return new ResponseEntity<Error>(error,HttpStatus.CONFLICT);
			}
			else
				return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			Error error=new Error(2,"Couldnt insert user details. Cannot have null/duplicate values " + e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?>logout(HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user!=null){
			user.setOnline(false);
			userDao.updateUser(user);
			try{
				String path="D:/instance/proj2backend/src/main/webapp/WEB-INF/resources/images/"+user.getUsername();
				File file=new File(path);
				System.out.println(file.delete());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET)
	public ResponseEntity<?>getAllUsers(HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null)
			return new ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
		else
		{
			List<User>users=userDao.getAllUsers(user);
			for(User u:users)
				System.out.println("IsOnline :" +u.isOnline());
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
	}
	
}
