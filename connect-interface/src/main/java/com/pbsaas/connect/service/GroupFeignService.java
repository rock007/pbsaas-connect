package com.pbsaas.connect.service;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.GroupMemberVO;
import com.pbsaas.connect.model.vo.GroupVO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "connect-provider",contextId = "groupFeignService")
@RequestMapping(value="/group")
public interface GroupFeignService {

    JsonBody<String> createGroup(NewGroupDTO dto);

    JsonBody<String> releaseGroup(ReleaseGroupDTO dto);

    JsonBody<String> updateGroup(ModifyGroupDTO dto);

    JsonBody<String> addMember(AddGroupMemberDTO m);

    JsonBody<String> removeMember(RemoveMemberDTO dto);

    JsonBody<PageModel<GroupVO>> searchGroups(SearchGroupDTO m, int page, int pageSize);

    JsonBody<PageModel<GroupMemberVO>> searchMembers(SearchGroupMemberDTO m, int page, int pageSize);

}
