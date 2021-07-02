/**
 * sam@here 2019/8/31
 **/
package com.pbsaas.connect.service.feign;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.GroupMemberVO;
import com.pbsaas.connect.model.vo.GroupVO;
import com.pbsaas.connect.service.GroupFeignService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupFeignServiceImpl implements GroupFeignService {
    @Override
    public JsonBody<String> createGroup(NewGroupDTO dto) {
        return null;
    }

    @Override
    public JsonBody<String> releaseGroup(ReleaseGroupDTO dto) {
        return null;
    }

    @Override
    public JsonBody<String> updateGroup(ModifyGroupDTO dto) {
        return null;
    }

    @Override
    public JsonBody<String> addMember(AddGroupMemberDTO m) {
        return null;
    }

    @Override
    public JsonBody<String> removeMember(RemoveMemberDTO dto) {
        return null;
    }

    @Override
    public JsonBody<PageModel<GroupVO>> searchGroups(SearchGroupDTO m, int page, int pageSize) {
        return null;
    }

    @Override
    public JsonBody<PageModel<GroupMemberVO>> searchMembers(SearchGroupMemberDTO m, int page, int pageSize) {
        return null;
    }
}
