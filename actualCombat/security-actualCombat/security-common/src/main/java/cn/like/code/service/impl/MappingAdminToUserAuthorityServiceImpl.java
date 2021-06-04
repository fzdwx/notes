package cn.like.code.service.impl;

import cn.like.code.entity.MappingAdminToUserAuthority;
import cn.like.code.mapper.MappingAdminToUserAuthorityMapper;
import cn.like.code.service.MappingAdminToUserAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户和用户职权的映射表.(MappingAdminToUserAuthority)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:13:59
 */
@Service
public class MappingAdminToUserAuthorityServiceImpl extends ServiceImpl<MappingAdminToUserAuthorityMapper, MappingAdminToUserAuthority> implements MappingAdminToUserAuthorityService {

}