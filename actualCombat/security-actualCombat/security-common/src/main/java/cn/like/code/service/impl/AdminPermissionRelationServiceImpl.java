package cn.like.code.service.impl;

import cn.like.code.entity.AdminPermissionRelation;
import cn.like.code.mapper.AdminPermissionRelationMapper;
import cn.like.code.service.AdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)(AdminPermissionRelation)表服务实现类
 *
 * @author like
 * @since 2021-06-02 12:23:50
 */
@Service
public class AdminPermissionRelationServiceImpl extends ServiceImpl<AdminPermissionRelationMapper, AdminPermissionRelation> implements AdminPermissionRelationService {

}