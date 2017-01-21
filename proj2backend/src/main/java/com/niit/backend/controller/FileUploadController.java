package com.niit.backend.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.backend.dao.FileUploadDao;
import com.niit.backend.model.UploadFile;
import com.niit.backend.model.User;

@RestController
public class FileUploadController {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private FileUploadDao fileUploadDao;
	
	@RequestMapping(value="/doUpload",method=RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request,HttpSession session,@RequestParam CommonsMultipartFile fileUpload)throws Exception {
		User user=(User) session.getAttribute("user");
		if(user==null)
			throw new RuntimeException("Not Logged in");
		System.out.println("User is " +user.getUsername());
		
		if(fileUpload !=null){
			CommonsMultipartFile aFile=fileUpload;
			System.out.println("Saving file :" +aFile.getOriginalFilename());
			
			UploadFile uploadFile=new UploadFile();
			uploadFile.setFileName(aFile.getOriginalFilename());
			uploadFile.setData(aFile.getBytes());
			uploadFile.setUsername(user.getUsername());
			fileUploadDao.save(uploadFile);
			
			UploadFile getUploadFile=fileUploadDao.getFile(user.getUsername());
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
		return "Successfully added profile pic";
	}
	@RequestMapping(value="/getFile",method=RequestMethod.GET)
	public ResponseEntity<?>getFile(HttpSession session){
		User user=(User) session.getAttribute("user");
		UploadFile uploadFile=fileUploadDao.getFile(user.getUsername());
		String name=uploadFile.getFileName();
		System.out.println(uploadFile.getData());
		byte[] imagefiles=uploadFile.getData();
		return new ResponseEntity<byte[]>(imagefiles,HttpStatus.OK);
	}

}
