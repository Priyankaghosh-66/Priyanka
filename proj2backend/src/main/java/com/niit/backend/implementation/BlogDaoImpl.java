package com.niit.backend.implementation;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.BlogDao;
import com.niit.backend.model.BlogComment;
import com.niit.backend.model.BlogPost;
import com.niit.backend.model.User;
@Repository
public class BlogDaoImpl implements BlogDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public List<BlogPost> getBlogPosts() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from BlogPost");
		List<BlogPost>blogPosts=query.list();
		session.close();
		return blogPosts;
	}

	@Override
	public BlogPost getBlogPost(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost) session.get(BlogPost.class, id);
		session.close();
		return blogPost;
	}

	@Override
	public BlogPost addBlogPost(User user, BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		blogPost.setCreatedBy(user);
		blogPost.setCreatedOn(new Date());
		session.save(blogPost);
		session.flush();
		BlogPost addedBlogPost=(BlogPost) session.get(BlogPost.class, blogPost.getId());
		return addedBlogPost;
		
	}

	@Override
	public List<BlogComment> getBlogComments(int blogId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost) session.get(BlogPost.class, blogId);
		List<BlogComment>blogComments=blogPost.getComments();
		session.close();
		
		
		return blogComments;
	}

	@Override
	public BlogPost addBlogPostComment(User user, BlogComment blogComment) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		blogComment.setCreatedBy(user);
		blogComment.setCreatedOn(new Date());
		BlogPost blogPost=(BlogPost) session.get(BlogPost.class,blogComment.getBlogPost().getId());
		blogComment.setBlogPost(blogPost);
		session.merge(blogComment);
		session.flush();
		session.close();
		return blogPost;
		
		
	}

}
