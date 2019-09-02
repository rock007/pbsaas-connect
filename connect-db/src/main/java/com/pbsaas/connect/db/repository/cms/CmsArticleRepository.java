package com.pbsaas.connect.db.repository.cms;

import com.pbsaas.connect.db.entity.cms.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CmsArticleRepository extends CrudRepository<Article, Long>,
												PagingAndSortingRepository<Article, Long> {

	Page<Article> findByCreateUserOrderByIdDesc(String username, Pageable pageable);
	
}