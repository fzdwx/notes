package cn.like.code.service.impl;

import cn.like.code.entity.Token;
import cn.like.code.mapper.TokenMapper;
import cn.like.code.service.TokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 访问令牌(Token)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:23
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

}