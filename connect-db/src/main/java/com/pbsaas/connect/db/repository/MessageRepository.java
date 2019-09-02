package com.pbsaas.connect.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pbsaas.connect.db.entity.Message;


/**
 * 
 * @author 
 *
 */
public interface MessageRepository extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message>{
       
	   List<Message> findAll();

       List<Message> findByStatus(Integer status);

       @Query(value="select a from sys_message a where a.msg_type=:msg_type "
       		+ "and a.content=:content and a.send_to=:send_to and  TIME_TO_SEC(TIMEDIFF(now(), a.create_date)) <= 30 ",nativeQuery=true)
       Message checkRepeatBy(@Param("msg_type") Integer msg_type,@Param("content") String content,@Param("send_to") String send_to);
}
