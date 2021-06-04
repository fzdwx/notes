package cn.like.code.service.impl;

import cn.like.code.entity.MappingClientToResourceServer;
import cn.like.code.mapper.MappingClientToResourceServerMapper;
import cn.like.code.service.MappingClientToResourceServerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.(MappingClientToResourceServer)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:14:05
 */
@Service
public class MappingClientToResourceServerServiceImpl extends ServiceImpl<MappingClientToResourceServerMapper, MappingClientToResourceServer> implements MappingClientToResourceServerService {

}