package cn.like.code.service.impl;

import cn.like.code.entity.AdminRoleRelation;
import cn.like.code.mapper.AdminRoleRelationMapper;
import cn.like.code.service.AdminRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 后台用户和角色关系表(AdminRoleRelation)表服务实现类
 *
 * @author like
 * @since 2021-06-02 12:23:52
 */
@Service
public class AdminRoleRelationServiceImpl extends ServiceImpl<AdminRoleRelationMapper, AdminRoleRelation> implements AdminRoleRelationService {

}