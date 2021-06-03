package cn.like.code.service.impl;

import cn.like.code.entity.Admin;
import cn.like.code.mapper.AdminMapper;
import cn.like.code.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 后台用户表(Admin)表服务实现类
 *
 * @author like
 * @since 2021-06-02 12:23:48
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}