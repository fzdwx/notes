package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Token;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访问令牌(Token)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:24
 */
@Mapper
public interface TokenMapper extends BaseMapper<Token> {
}