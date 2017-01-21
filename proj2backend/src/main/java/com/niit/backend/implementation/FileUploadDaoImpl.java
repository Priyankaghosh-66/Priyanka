package com.niit.backend.implementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.FileUploadDao;
import com.niit.backend.model.UploadFile;

@Repository
public class FileUploadDaoImpl implements FileUploadDao{

	
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void save(UploadFile uploadFile) {
		Session session=sessionFactory.openSession();
		session.saveOrUpdate(uploadFile);
		session.flush();
		session.close();
		// TODO Auto-generated method stub
		
	}

	@Override
	public UploadFile getFile(String username) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from UploadFile where username=?");
		query.setParameter(0,username);
		UploadFile uploadFile=(UploadFile) query.setMaxResults(1).uniqueResult();
		session.close();
		// TODO Auto-generated method stub
		return uploadFile;
	}

}
