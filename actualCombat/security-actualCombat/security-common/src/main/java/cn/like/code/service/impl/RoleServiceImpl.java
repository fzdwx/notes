package cn.like.code.service.impl;

import cn.like.code.entity.Role;
import cn.like.code.mapper.RoleMapper;
import cn.like.code.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 后台用户角色表(Role)表服务实现类
 *
 * @author like
 * @since 2021-06-02 12:23:57
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}