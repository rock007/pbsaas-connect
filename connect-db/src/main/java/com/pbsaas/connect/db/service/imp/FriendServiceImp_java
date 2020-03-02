/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月17日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pbsaas.connect.db.entity.ChatFriend;
import com.pbsaas.connect.db.entity.ChatFriendIds;
import com.pbsaas.connect.db.entity.ChatGroup;
import com.pbsaas.connect.db.entity.ChatGroupMember;
import com.pbsaas.connect.db.entity.ChatGroupMemberIds;
import com.pbsaas.connect.db.repository.ChatFriendRepository;
import com.pbsaas.connect.db.repository.ChatGroupMemberRepository;
import com.pbsaas.connect.db.repository.ChatGroupRepository;
import com.pbsaas.connect.db.service.FriendService;

/**
 * @author sam
 *
 */
@Component("friendService")
public class FriendServiceImp implements FriendService{

	@Autowired
	private ChatFriendRepository chatFriendRepository;
	
	@Autowired
	private ChatGroupRepository chatGroupRepository;
	
	@Autowired
	private ChatGroupMemberRepository chatGroupMemberRepository;

    @Autowired
    private  JdbcTemplate jdbcTemplate;

	@Override
	public ChatFriend saveFriend(ChatFriend m) {
		
		return chatFriendRepository.save(m);
	}

	@Override
	public void deleteFriend(String from, String to) {
		
		chatFriendRepository.deleteById(new ChatFriendIds(from,to));

	}

	@Override
	public ChatGroup saveGroup(ChatGroup m) {
		
		return chatGroupRepository.save(m);
	}

	@Override
	public void deleteGroup(Long id) {
		
		chatGroupRepository.deleteById(id);
	}

	@Override
	public ChatGroupMember saveGroupMember(ChatGroupMember m) {
		
		return chatGroupMemberRepository.save(m);
	}
	
	@Override
	public ChatFriend findOneFriend(String to_user, String friend_user) {
		
		return chatFriendRepository.findById(new ChatFriendIds(to_user,friend_user)).orElse(null);
	}

	@Override
	public ChatGroup findOneGroup(Long id) {
		
		return chatGroupRepository.findById(id).orElse(null);
	}

	@Override
	public Page<ChatFriend> searchFriends(ChatFriend m, int page, int pageSize) {
		return chatFriendRepository.findAll(new Specification<ChatFriend>() {

			public Predicate toPredicate(Root<ChatFriend> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String nick_name = m.getNick_name();//用户姓名

				String to_user = m.getIds()!=null? m.getIds().getTo_user():"";// 申请者,本人
				String friend_user = m.getIds()!=null?m.getIds().getFriend_user():"";//接收者
				
				Integer req_status = m.getReq_status(); //状态
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(nick_name)) {
					p1 = cb.like(root.get("nick_name").as(String.class), "%"
							+ nick_name.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("nick_name").as(String.class), "0");
					pc=cb.and(p1);
				}
				
				if(!StringUtils.isEmpty(to_user)){
					
					p2 =cb.equal(root.get("ids").get("to_user").as(String.class),to_user);
					pc=cb.and(pc,p2);
				}
				
				if(!StringUtils.isEmpty(friend_user)){
					
					p3 =cb.equal(root.get("ids").get("friend_user").as(String.class),friend_user);
					pc=cb.and(pc,p3);
				}
				
				if(req_status != null){
	
					p4 =cb.equal(root.get("req_status").as(Integer.class),req_status);
					pc=cb.and(pc,p4);
				}
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.asc(root.get("nick_name").as(String.class)));

				return null;
			}

		}, PageRequest.of(page, pageSize));
	}

	@Override
	public Page<ChatGroup> searchGroups(ChatGroup m, int page, int pageSize) {
		return chatGroupRepository.findAll(new Specification<ChatGroup>() {

			public Predicate toPredicate(Root<ChatGroup> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String group_name = m.getGroup_name();

				String own_user = m.getOwn_user();
				
				Integer status = m.getStatus(); //状态
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(group_name)) {
					p1 = cb.like(root.get("group_name").as(String.class), "%"
							+ group_name.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(String.class), "0");
					pc=cb.and(p1);
				
				}
				
				if(!StringUtils.isEmpty(own_user)){
					
					p2 =cb.equal(root.get("own_user").as(String.class),own_user);
					pc=cb.and(pc,p2);
				}

				if(status != null){
	
					p4 =cb.equal(root.get("status").as(Integer.class),status);
					pc=cb.and(pc,p4);
				}

				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.asc(root.get("group_name").as(String.class)));

				return null;
			}

		}, PageRequest.of(page, pageSize));
	}

	@Override
	public Page<ChatGroupMember> searchGroupMembers(ChatGroupMember m, int page, int pageSize) {
		
		return chatGroupMemberRepository.findAll(new Specification<ChatGroupMember>() {

			public Predicate toPredicate(Root<ChatGroupMember> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String nick_name = m.getNick_name();

				String user_id = m.getIds()!=null?m.getIds().getUser_id():"";
				
				Long group_id = m.getIds()!=null?m.getIds().getGroup_id():0L;
				
				//Integer status = m.getStatus(); //状态
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(nick_name)) {
					p1 = cb.like(root.get("nick_name").as(String.class), "%"
							+ nick_name.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("ids").get("group_id").as(String.class), "0");
					pc=cb.and(p1);
				
				}
				
				if(!StringUtils.isEmpty(user_id)){
					
					p2 =cb.equal(root.get("ids").get("user_id").as(String.class),user_id);
					pc=cb.and(pc,p2);
				}

				if(group_id != null){
	
					p4 =cb.equal(root.get("ids").get("group_id").as(Long.class),group_id);
					pc=cb.and(pc,p4);
				}

				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.asc(root.get("nick_name").as(String.class)));

				return null;
			}

		}, PageRequest.of(page, pageSize));
	}
	
	@Override
	public List<ChatGroup> getGroupsByUserId(String userId) {
		
		return chatGroupRepository.getGroupsByUserId(userId);
	}
	
	@Override
	public  void deleteGroupMember(Long gid,String uid){
		
		ChatGroupMemberIds id=new ChatGroupMemberIds(gid,uid);
		
		chatGroupMemberRepository.deleteById(id);
	}

    @Override
    public List<Map<String, String>> getMyFriends(String to_user, int req_status) {

        return jdbcTemplate.query(" select * from v_friend where to_user='"+to_user+"' and req_status="+req_status, new RowMapper<Map<String, String>>() {

            @Override
            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {

                Map<String,String> row = new HashMap<>();
                row.put("to_user", rs.getString("to_user"));
                row.put("uid", rs.getString("id"));
                row.put("username", rs.getString("account_name"));
                row.put("mobile", rs.getString("mobile"));
                row.put("sex", rs.getString("sex"));

                row.put("province", rs.getString("province"));
                row.put("city", rs.getString("city"));
                row.put("level", rs.getString("user_level"));
                row.put("money", rs.getString("user_money"));
                row.put("avatar", rs.getString("avatar"));

                row.put("reg_from", rs.getString("reg_from"));
                row.put("status", rs.getString("status"));
                row.put("birthday", rs.getString("birthday"));
                row.put("nickname", rs.getString("nickname"));
                row.put("nick_name", rs.getString("nick_name"));
                row.put("req_status", rs.getString("req_status"));
                row.put("accept_date", rs.getDate("accept_date")!=null?Long.valueOf(rs.getDate("accept_date").getTime()).toString():"");
                return row;
            }
        });

    }

    @Override
    public List<Map<String, String>> getGroupMembers(String group_id) {

        return jdbcTemplate.query("select * from v_group_member where groupId = "+group_id, new RowMapper<Map<String, String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {

                Map<String,String> row = new HashMap<>();
                row.put("groupId", rs.getString("groupId"));
                row.put("group_name", rs.getString("group_name"));
                row.put("note", rs.getString("note"));
                row.put("remarks", rs.getString("remarks"));
                row.put("status", rs.getString("status"));
                row.put("group_owner", rs.getString("group_owner"));

                row.put("group_level", rs.getString("group_level"));
                row.put("create_date", rs.getString("create_date"));

                row.put("uid", rs.getString("id"));
                row.put("username", rs.getString("account_name"));
                row.put("mobile", rs.getString("mobile"));
                row.put("sex", rs.getString("sex"));

                row.put("province", rs.getString("province"));
                row.put("city", rs.getString("city"));
                row.put("level", rs.getString("user_level"));
                row.put("money", rs.getString("user_money"));
                row.put("avatar", rs.getString("avatar"));

                row.put("reg_from", rs.getString("reg_from"));
                row.put("birthday", rs.getString("birthday"));
                row.put("nickname", rs.getString("nickname"));
                return row;
            }
        });
    }
}
