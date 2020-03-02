package com.pbsaas.connect.service;

import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.GroupMemberVO;
import com.pbsaas.connect.model.vo.GroupVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

@FeignClient(value = "groupFeignService")
public interface GroupFeignService {

    ResultVO<String> createGroup(NewGroupDTO dto);

    ResultVO<String> releaseGroup(ReleaseGroupDTO dto);

    ResultVO<String> updateGroup(ModifyGroupDTO dto);

    ResultVO<String> addMember(AddGroupMemberDTO m);

    ResultVO<String> removeMember(RemoveMemberDTO dto);

    PageVO<GroupVO> searchGroups(SearchGroupDTO m, int page, int pageSize);

    PageVO<GroupMemberVO> searchMembers(SearchGroupMemberDTO m, int page, int pageSize);

}
