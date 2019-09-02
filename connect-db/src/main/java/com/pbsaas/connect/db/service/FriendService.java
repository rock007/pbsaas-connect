/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月17日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service;

import java.util.List;
import java.util.Map;

import com.pbsaas.connect.db.entity.ChatFriend;
import com.pbsaas.connect.db.entity.ChatGroup;
import com.pbsaas.connect.db.entity.ChatGroupMember;
import org.springframework.data.domain.Page;

/**
 * @author sam
 *
 */
public interface FriendService {

	ChatFriend saveFriend(ChatFriend m);
	
	void deleteFriend(String from,String to);
	
	ChatFriend findOneFriend(String to_user,String friend_user);
	
	Page<ChatFriend> searchFriends(ChatFriend m, int page, int pageSize);
	
	ChatGroup saveGroup(ChatGroup m);
	
	void deleteGroup(Long id);
	
	ChatGroup findOneGroup(Long id);
	
	ChatGroupMember saveGroupMember(ChatGroupMember m);
	
	void deleteGroupMember(Long gid,String uid);
	
	Page<ChatGroup> searchGroups(ChatGroup m, int page, int pageSize);
	
	Page<ChatGroupMember> searchGroupMembers(ChatGroupMember m, int page, int pageSize);
	
	List<ChatGroup> getGroupsByUserId(String userId);

	List<Map<String,String>> getMyFriends(String to_user, int req_status);

    List<Map<String,String>> getGroupMembers(String group_id);

}
