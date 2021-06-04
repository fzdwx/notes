package cn.like.code.service.impl;

import cn.like.code.entity.Resource;
import cn.like.code.mapper.ResourceMapper;
import cn.like.code.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 资源. 代表着形如 /user/1 的具体的资源本身.(Resource)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:14:07
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}