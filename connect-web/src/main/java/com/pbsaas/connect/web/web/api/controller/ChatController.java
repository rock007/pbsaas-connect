package com.pbsaas.connect.web.web.api.controller;

import java.util.Date;
import java.util.Map;

import com.google.protobuf.Any;

import com.pbsaas.connect.proto.PaintFriend;
import com.pbsaas.connect.web.web.model.JsonBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pbsaas.connect.db.entity.ChatMessage;


/**
 * @author sam
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value="/api")
public class ChatController   extends JsonBaseController {

	@RequestMapping(value="/un-read-msg.json",method = RequestMethod.GET)
	public @ResponseBody  String[][] un_read_msg(Map<String, Object> model) {
		
		model.put("time", new Date());
		model.put("message", "hello the world");
		
		return null;
	}
	
	
	@PostMapping(value="/send-msg.json")
	public @ResponseBody
    JsonBody<ChatMessage> send_msg(String chatType, String to, Integer msgType, String msg, String uid) {

        PaintFriend.MsgBody  msgBody= PaintFriend.MsgBody.newBuilder()
                .setChatType(chatType.equals("group")? PaintFriend.ChatType.CHAT_TYPE_GROUP_VALUE: PaintFriend.ChatType.CHAT_TYPE_FRIEND_VALUE)
                .setMsgType(msgType)
                .setText(msg)
                .setUrl("")
                .setFrom(uid)
                .setTo(to)
                .setTime(Long.valueOf(new Date().getTime()).intValue())
                .build();

        PaintFriend.ReqBody     req = PaintFriend.ReqBody.newBuilder()
                .setActid(PaintFriend.ActID.ACT_ID_SEND_MSG_VALUE)
                .setText("send-msg-action")
                .setToken("")
                .setUid(uid)
                .setData(Any.pack(msgBody))
                .build();
/**!!
        if(!SendMsgQueue.getQueue().offer(req)){

            return new JsonBody<>(1,"发送失败",null);
        }
***/
		return new JsonBody<>(1,"发送成功",null);
	}
/**

    @RequestMapping(value="/connect-list.json",method = RequestMethod.GET)
    public @ResponseBody Collection<ConnInfo> connect_list() {

        Map<Channel,ConnInfo> map= SessionPool.getAll();

        return map.values();
    }
    **/
}
