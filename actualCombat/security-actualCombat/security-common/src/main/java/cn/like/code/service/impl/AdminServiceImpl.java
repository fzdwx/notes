package cn.like.code.service.impl;

import cn.like.code.entity.Admin;
import cn.like.code.entity.dto.AdminDTO;
import cn.like.code.mapper.AdminMapper;
import cn.like.code.mapper.MappingAdminToAdminAuthorityMapper;
import cn.like.code.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台用户表(Admin)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private MappingAdminToAdminAuthorityMapper mappingAdminToUserAuthorityMapper;

    @Override
    public AdminDTO getAdmin(String username) {
        final AdminDTO admin = getBaseMapper().getAdmin(username);
        admin.setAuthorities(mappingAdminToUserAuthorityMapper.getUserAuthorities(admin.getId()));
        return admin;
    }
}