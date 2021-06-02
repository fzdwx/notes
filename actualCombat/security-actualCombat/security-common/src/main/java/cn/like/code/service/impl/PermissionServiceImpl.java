package cn.like.code.service.impl;

import cn.like.code.base.BaseServiceImpl;
import cn.like.code.entity.Permission;
import cn.like.code.mapper.PermissionMapper;
import cn.like.code.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * 后台用户权限表(Permission)表服务实现类
 *
 * @author like
 * @since 2021-06-02 12:23:54
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

}