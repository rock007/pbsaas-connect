package com.pbsaas.connect.db.service;
import java.util.List;

import com.pbsaas.connect.db.entity.Message;
import org.springframework.data.domain.Page;


/**
 * 
 * @author
 *
 */
public interface MessageService {
	
    public Message save(Message m);
	
    public List<Message> getAll();
    
    public List<Message> getStatus(Integer status);
	
	public Message findById(Long id);
	
    public void delete(Long id);
    
    public Page<Message> search( Message m,int page,int pageSize);

}
