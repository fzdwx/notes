package cn.like.code.service.impl;

import cn.like.code.entity.MappingAdminAuthorityToResource;
import cn.like.code.mapper.MappingAdminAuthorityToResourceMapper;
import cn.like.code.service.MappingAdminAuthorityToResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户职权和资源的映射表.(MappingAdminAuthorityToResource)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:13:57
 */
@Service
public class MappingAdminAuthorityToResourceServiceImpl extends ServiceImpl<MappingAdminAuthorityToResourceMapper, MappingAdminAuthorityToResource> implements MappingAdminAuthorityToResourceService {

}