package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.BlogComment;
import com.niit.backend.model.BlogPost;
import com.niit.backend.model.User;

public interface BlogDao {

	List<BlogPost>getBlogPosts();
	BlogPost getBlogPost(int id);
	BlogPost addBlogPost(User user,BlogPost blogPost);
	List<BlogComment>getBlogComments(int blogId);
	BlogPost addBlogPostComment(User user,BlogComment blogComment);
	
}
