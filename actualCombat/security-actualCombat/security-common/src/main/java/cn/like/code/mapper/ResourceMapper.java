package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源. 代表着形如 /user/1 的具体的资源本身.(Resource)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:14:08
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
}