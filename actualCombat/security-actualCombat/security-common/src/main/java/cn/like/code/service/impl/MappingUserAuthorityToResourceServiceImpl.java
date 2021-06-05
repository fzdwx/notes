package cn.like.code.service.impl;

import cn.like.code.entity.MappingUserAuthorityToResource;
import cn.like.code.mapper.MappingUserAuthorityToResourceMapper;
import cn.like.code.service.MappingUserAuthorityToResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户职权和资源的映射表.(MappingUserAuthorityToResource)表服务实现类
 *
 * @author pig4cloud
 * @since 2021-06-05 10:05:01
 */
@Service
public class MappingUserAuthorityToResourceServiceImpl extends ServiceImpl<MappingUserAuthorityToResourceMapper, MappingUserAuthorityToResource> implements MappingUserAuthorityToResourceService {

}