/**
 * sam@here 2019/8/31
 **/
package com.pbsaas.connect.service.feign;

import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.GroupMemberVO;
import com.pbsaas.connect.model.vo.GroupVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import com.pbsaas.connect.service.GroupFeignService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupFeignServiceImpl implements GroupFeignService {
    @Override
    public ResultVO<String> createGroup(NewGroupDTO dto) {
        return null;
    }

    @Override
    public ResultVO<String> releaseGroup(ReleaseGroupDTO dto) {
        return null;
    }

    @Override
    public ResultVO<String> updateGroup(ModifyGroupDTO dto) {
        return null;
    }

    @Override
    public ResultVO<String> addMember(AddGroupMemberDTO m) {
        return null;
    }

    @Override
    public ResultVO<String> removeMember(RemoveMemberDTO dto) {
        return null;
    }

    @Override
    public PageVO<GroupVO> searchGroups(SearchGroupDTO m, int page, int pageSize) {
        return null;
    }

    @Override
    public PageVO<GroupMemberVO> searchMembers(SearchGroupMemberDTO m, int page, int pageSize) {
        return null;
    }
}
