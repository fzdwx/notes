package cn.like.code.service.impl;

import cn.like.code.entity.Authorities;
import cn.like.code.mapper.AuthoritiesMapper;
import cn.like.code.service.AuthoritiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户权限(Authorities)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:15
 */
@Service
public class AuthoritiesServiceImpl extends ServiceImpl<AuthoritiesMapper, Authorities> implements AuthoritiesService {

}