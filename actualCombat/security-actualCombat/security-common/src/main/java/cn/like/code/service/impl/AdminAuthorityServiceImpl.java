package cn.like.code.service.impl;

import cn.like.code.entity.AdminAuthority;
import cn.like.code.mapper.AdminAuthorityMapper;
import cn.like.code.service.AdminAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).(AdminAuthority)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:13:50
 */
@Service
public class AdminAuthorityServiceImpl extends ServiceImpl<AdminAuthorityMapper, AdminAuthority> implements AdminAuthorityService {

}