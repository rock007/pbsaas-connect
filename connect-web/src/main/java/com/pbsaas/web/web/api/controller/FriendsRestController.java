package com.pbsaas.web.web.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.paintfriend.chat.proto.PaintFriend;
import com.pbsaas.web.web.model.JsonBody;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.ChatFriend;
import com.pbsaas.connect.db.entity.ChatFriendIds;
import com.pbsaas.connect.db.entity.ChatGroup;
import com.pbsaas.connect.db.entity.ChatGroupMember;
import com.pbsaas.connect.db.entity.ChatGroupMemberIds;
import com.pbsaas.connect.db.service.AccountService;
import com.pbsaas.connect.db.service.FriendService;

/**
 * @author sam
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value="/api")
public class FriendsRestController  extends JsonBaseController{

	@Autowired
	AccountService accountService;
	
	@Autowired
	private FriendService friendService;

	@ApiOperation(value="获得我的好友", notes="获得我的好友、好友申请列表，有分页")
	@RequestMapping(value="/my-friends.json",method = RequestMethod.GET)
		public @ResponseBody  JsonBody<List<Map<String,String>> > my_friends(int req_status) {

        String userId=getAccInfo().getId();

        List<Map<String,String>> list = friendService.getMyFriends(userId, req_status);

        return new JsonBody<List<Map<String,String>> >(1, "获取数据成功", list);
    }
		
		@GetMapping("/my-groups.json")
		public @ResponseBody  JsonBody<List<ChatGroup>> my_groups(@ModelAttribute ChatGroup m
				,@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex
				,@RequestParam(value="pageSize",required=false,defaultValue="10")  int pageSize) {
			
			String userId=getCurUser().getUsername();
			
			List<ChatGroup> groups= friendService.getGroupsByUserId(userId);
			
			return new JsonBody<List<ChatGroup>>(1,"获取数据成功",groups);
			
		}
		
		@GetMapping("/get-group-members.json")
		public @ResponseBody  JsonBody<List<Map<String,String>>> my_group_members(Long gid) {

			List<Map<String,String>> list= friendService.getGroupMembers(gid.toString());
			
			return new JsonBody<List<Map<String,String>>>(1,"获取数据成功",list);
		}
		
		@GetMapping("/get-friend-info.json")
		public @ResponseBody  JsonBody<Account> get_friend_info(String uid) {
			
			//ChatFriend friend= friendService.findOneFriend(getCurUser().getUsername(), uid);
			
			Account acc= accountService.findById(uid);
			
			return new JsonBody<Account>(1,"获取数据成功",acc);
		}
		
	
		@RequestMapping(value="/ask-friend-req.json" ,method=RequestMethod.POST)
		public @ResponseBody  JsonBody<String> ask_friend_req(String to_user,String nickname) {

			ChatFriend m=new ChatFriend();
			//check
			if( StringUtils.isEmptyOrWhitespace(to_user)){
				
				return new JsonBody<>(-1,"to_user不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(nickname)){
		
				return new JsonBody<>(-1,"nickname 不能为空");
			}

			String userId=getAccInfo().getId();

            m= friendService.findOneFriend(to_user,userId);
            if(m!=null){

                if(m.getReq_status()==1){

                    return new JsonBody<>(-2,"你们已经是好友");

                }else if(m.getReq_status()==0){
                    return new JsonBody<>(-2,"您已发送过好友请求");
                }

            }

            m=new ChatFriend();
			m.setIds(new ChatFriendIds(to_user,userId));
			m.setNick_name(nickname);
			m.setCreate_date(new Date());
			m.setReq_status(0);
			friendService.saveFriend(m);

            PaintFriend.Param param=PaintFriend.Param.newBuilder()
                    .setKey("uid")
                    .setValue(userId).build();
            pushNotify("好友请求申请",to_user,param);

			return new JsonBody<String>(1,"发送好友请求成功");
		}

		@PostMapping("/accept-friend-req.json")
		public @ResponseBody JsonBody<String> accept_friend_req(String friend_user,String nickname) {

            String to_user=getAccInfo().getId();

			ChatFriend oneFriend= friendService.findOneFriend(to_user, friend_user);

            if(oneFriend==null){

                return new JsonBody<String>(-1,"好友请求已过期");
            }
			oneFriend.setReq_status(1);
			oneFriend.setAccept_date(new Date());
			
			friendService.saveFriend(oneFriend);

			//自动加好友
            ChatFriend m=new ChatFriend();
            m.setIds(new ChatFriendIds(friend_user,to_user));
            m.setNick_name(nickname);
            m.setCreate_date(new Date());
            m.setAccept_date(new Date());
            m.setReq_status(1);
            friendService.saveFriend(m);

            pushNotify("同意好友请求",friend_user);

			return new JsonBody<String>(1,"好友申请已同意");
		}

    @PostMapping("/reject-friend-req.json")
    public @ResponseBody JsonBody<String> reject_friend_req(String friend_user) {

        String to_user=getAccInfo().getId();

        ChatFriend oneFriend= friendService.findOneFriend(to_user, friend_user);

        if(oneFriend!=null){

            friendService.deleteFriend(to_user, friend_user);

        }else {

            return new JsonBody<String>(-1,"好友请求已过期");
        }

        return new JsonBody<String>(1,"好友请求已拒绝");
    }

		@PostMapping("/del-friend.json")
		public @ResponseBody  JsonBody<String> del_friend(String from,String to) {
			
			friendService.deleteFriend(from, to);
            friendService.deleteFriend(to, from);

            pushNotify("删除好友",from);

			return new JsonBody<String>(1,"删除好友成功");
		}
		
		@GetMapping("/get-group-info.json")
		public @ResponseBody JsonBody<ChatGroup> get_group_info(Long gid) {
			
			ChatGroup group= friendService.findOneGroup(gid);
			
			return new JsonBody<ChatGroup>(1,"获取数据成功",group);
		}

		@PostMapping("/create-group.json")
		public @ResponseBody JsonBody<String> create_group(String name,String[] uids) {

            ChatGroup m=new ChatGroup();
            m.setGroup_name(name);
            m.setCreate_date(new Date());
			m.setGroup_level(1);
			m.setOwn_user(this.getAccInfo().getId());
			m.setStatus(0);
			m=friendService.saveGroup(m);

			if(m!=null){

                for(String u:uids){

                    if(u!=null&&!"".equals(u)){

                        Account acc= accountService.findById(u);

                        if(acc==null) continue;

                        ChatGroupMember member=new ChatGroupMember();

                        member.setIds(new ChatGroupMemberIds(m.getId(),u));

                        member.setNick_name(acc.getNickname());

                        friendService.saveGroupMember(member);

                        //加通知
                        pushNotify("加入群组",u);

                    }
                }
                //加上本人
                ChatGroupMember member=new ChatGroupMember();

                member.setIds(new ChatGroupMemberIds(m.getId(),m.getOwn_user()));

                member.setNick_name(this.getAccInfo().getNickname());

                friendService.saveGroupMember(member);
            }

			return  new JsonBody<String>(1,"创建群组成功");
		}
		
		@PostMapping("/del-group.json")
		public @ResponseBody JsonBody<String> del_group(Long gid) {

            ChatGroup group= friendService.findOneGroup(gid);

            if(group==null){

                return  new JsonBody<String>(-100,"群组不存在");
            }

            if(!group.getOwn_user().equals(getCurUser().getUsername())){

                return  new JsonBody<String>(-101,"仅群主可解散群组");
            }

			ChatGroupMember gm=new ChatGroupMember();
			gm.setIds(new ChatGroupMemberIds(gid,""));
			//删除成员
			List<ChatGroupMember> members= friendService.searchGroupMembers(gm, 0, 1000).getContent();
			
			for(ChatGroupMember mm:members){
				
				friendService.deleteGroupMember(mm.getIds().getGroup_id(), mm.getIds().getUser_id());

                pushNotify("群组解散",mm.getIds().getUser_id());
			}
			
			friendService.deleteGroup(gid);

			return  new JsonBody<String>(1,"删除群组成功");
		}

		@PostMapping("/add-group-mebmer.json")
		public @ResponseBody JsonBody<String> add_group_member(Long gid,String[] uids) {
			
			for(String u:uids){
				
				if(u!=null&&!"".equals(u)){

                    Account acc= accountService.findById(u);

                    if(acc==null) continue;

					ChatGroupMember member=new ChatGroupMember();
					
					member.setIds(new ChatGroupMemberIds(gid,u));
					
					member.setNick_name(acc.getNickname());
					
					friendService.saveGroupMember(member);

					//加通知
                    pushNotify("加入群组",u);

				}
			}
			return  new JsonBody<String>(1,"添加群组成员操作成功");
		}
		
		@PostMapping("/quit-group.json")
		public @ResponseBody JsonBody<String> quit_group(Long gid) {
			
			friendService.deleteGroupMember(gid,getCurUser().getUsername());

			return  new JsonBody<String>(1,"退出群组成功");
		}

    @GetMapping("/get-profile.json")
    public @ResponseBody JsonBody<Account> get_profile(@RequestParam(value="uid",required=false,defaultValue="") String uid,String where) {

	    if(where.equals("my")){

	        uid= this.getAccInfo().getId();

        }else{

	        if(uid==null||"".equals(uid)){

                return  new JsonBody<Account>(-1,"参数错误，uid不能为空");

            }

        }

        Account acc= accountService.findById(uid);

        return  new JsonBody<Account>(1,"获取数据成功",acc);
    }

}
