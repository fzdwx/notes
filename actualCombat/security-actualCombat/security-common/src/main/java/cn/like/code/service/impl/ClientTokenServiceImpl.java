package cn.like.code.service.impl;

import cn.like.code.entity.ClientToken;
import cn.like.code.mapper.ClientTokenMapper;
import cn.like.code.service.ClientTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 客户token表(ClientToken)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:19
 */
@Service
public class ClientTokenServiceImpl extends ServiceImpl<ClientTokenMapper, ClientToken> implements ClientTokenService {

}