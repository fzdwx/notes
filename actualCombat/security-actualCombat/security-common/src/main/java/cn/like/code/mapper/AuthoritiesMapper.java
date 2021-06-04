package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Authorities;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户权限(Authorities)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:17
 */
@Mapper
public interface AuthoritiesMapper extends BaseMapper<Authorities> {
}