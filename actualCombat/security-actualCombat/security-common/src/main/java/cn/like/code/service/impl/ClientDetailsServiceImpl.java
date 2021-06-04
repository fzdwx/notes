package cn.like.code.service.impl;

import cn.like.code.entity.ClientDetails;
import cn.like.code.mapper.ClientDetailsMapper;
import cn.like.code.service.ClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 认证客户端详情表(ClientDetails)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:17
 */
@Service
public class ClientDetailsServiceImpl extends ServiceImpl<ClientDetailsMapper, ClientDetails> implements ClientDetailsService {

}