package com.niit.backend.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.JobDao;
import com.niit.backend.model.Job;

@Repository
public class JobDaoImpl implements JobDao{
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void postJob(Job job) {
		// TODO Auto-generated method stub
		logger.debug("ENTERING JOBDAOIMPL");
		Session session=sessionFactory.openSession();
		session.save(job);
		session.flush();
		logger.debug("JOBID IN DAO"+job.getJobId());
		session.close();
		
	}

	@Override
	public List<Job> getAllJobs() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Job");
		List<Job>jobs=query.list();
		session.close();
		return jobs;
	}

	@Override
	public Job getJobDetail(int jobId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Job job=(Job) session.get(Job.class, jobId);
		session.close();
		return job;
	}

}
