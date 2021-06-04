package cn.like.code.service.impl;

import cn.like.code.entity.ResourceServer;
import cn.like.code.mapper.ResourceServerMapper;
import cn.like.code.service.ResourceServerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 资源服务器. 可提供客户端访问的资源服务器定义.(ResourceServer)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:14:09
 */
@Service
public class ResourceServerServiceImpl extends ServiceImpl<ResourceServerMapper, ResourceServer> implements ResourceServerService {

}