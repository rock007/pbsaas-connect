/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月17日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pbsaas.connect.db.entity.ChatMessage;
import com.pbsaas.connect.db.repository.ChatMessageRepository;
import com.pbsaas.connect.db.service.ChatService;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author sam
 *
 */
@Component("chatService")
public class ChatServiceImp implements ChatService{

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Override
	public ChatMessage saveMessage(ChatMessage m) {
		
		return chatMessageRepository.save(m);
	}

	@Override
	public void deleteMessage(Long id) {

		chatMessageRepository.deleteById(id);
	}

    @Override
    public Page<ChatMessage> search(final ChatMessage m, int page, int pageSize) {

        return chatMessageRepository.findAll(new Specification<ChatMessage>() {

            public Predicate toPredicate(Root<ChatMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                String send_from = m.getSend_from();
                Integer status = m.getStatus(); //状态
                String to_user=m.getTo_user();

                Predicate p1,p2,p3,p4,pc;

                if(status!=null){

                    p1=cb.equal(root.get("status").as(Integer.class), status);
                    pc=cb.and(p1);
                }else{

                    p1=cb.notEqual(root.get("status").as(Integer.class), 99);
                    pc=cb.and(p1);
                }

                if(!StringUtils.isEmpty(to_user)){

                    p2=cb.equal(root.get("to_user").as(String.class), to_user);
                    pc=cb.and(p2);
                }

                if(!StringUtils.isEmpty(send_from)){

                    p3=cb.equal(root.get("send_from").as(String.class), send_from);
                    pc=cb.and(p3);
                }

                query.where(pc);

                // 添加排序的功能
                query.orderBy(cb.desc(root.get("send_date").as(Date.class)));

                return null;
            }
        },  PageRequest.of(page, pageSize));
    }

	
}
