package com.pbsaas.connect.db.service.imp;


import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.db.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pbsaas.connect.db.entity.Message;
import com.pbsaas.connect.db.service.MessageService;

/**
 * 
 * @author 
 *
 */
@Component("MessageService")
public class MessageServiceImp implements MessageService {
	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Message save(Message m) {

		return messageRepository.save(m);
	}

	@Override
	public List<Message> getAll() {

		return messageRepository.findAll();
	}

	@Override
	public Message findById(Long id) {

		return messageRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {

		messageRepository.deleteById(id);
	}

	@Override
	public List<Message> getStatus(Integer status) {

		return messageRepository.findByStatus(status);
	}

	@Override
	public Page<Message> search(final Message m, int page, int pageSize) {

		return messageRepository.findAll(new Specification<Message>() {

			public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 
				String send_from = m.getSend_from();
				Integer status = m.getStatus(); //状态
				String send_to=m.getSend_to();
					
				Predicate p1,p2,p3,p4,pc;
					
				if(status!=null){
					 
					 p1=cb.equal(root.get("status").as(Integer.class), status);
					 pc=cb.and(p1);
				 }else{
					 
					 p1=cb.notEqual(root.get("status").as(Integer.class), 99);
					 pc=cb.and(p1);
				 }
				
				if(!StringUtils.isEmpty(send_to)){
					
					 p2=cb.equal(root.get("send_to").as(String.class), send_to);
					 pc=cb.and(p2);	
				}
				
				if(!StringUtils.isEmpty(send_from)){
					
					 p3=cb.equal(root.get("send_from").as(String.class), send_from);
					 pc=cb.and(p3);	
				}
				 
				query.where(pc);
				
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("create_date").as(Date.class)));
				
				return null;
			}
		},  PageRequest.of(page, pageSize));
	}

}
