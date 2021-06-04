package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ClientDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 认证客户端详情表(ClientDetails)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:18
 */
@Mapper
public interface ClientDetailsMapper extends BaseMapper<ClientDetails> {
}