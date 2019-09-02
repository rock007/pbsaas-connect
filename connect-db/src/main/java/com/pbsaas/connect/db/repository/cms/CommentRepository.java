package com.pbsaas.connect.db.repository.cms;

import com.pbsaas.connect.db.entity.cms.Comment;
import org.springframework.data.repository.CrudRepository;


public interface CommentRepository extends CrudRepository<Comment, Long> {
	
}