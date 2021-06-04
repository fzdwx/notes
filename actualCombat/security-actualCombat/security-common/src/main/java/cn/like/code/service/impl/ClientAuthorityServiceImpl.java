package cn.like.code.service.impl;

import cn.like.code.entity.ClientAuthority;
import cn.like.code.mapper.ClientAuthorityMapper;
import cn.like.code.service.ClientAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).(ClientAuthority)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:13:55
 */
@Service
public class ClientAuthorityServiceImpl extends ServiceImpl<ClientAuthorityMapper, ClientAuthority> implements ClientAuthorityService {

}