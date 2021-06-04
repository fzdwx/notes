package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ClientToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户token表(ClientToken)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:20
 */
@Mapper
public interface ClientTokenMapper extends BaseMapper<ClientToken> {
}