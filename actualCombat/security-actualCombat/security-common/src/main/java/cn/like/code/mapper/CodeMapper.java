package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Code;
import org.apache.ibatis.annotations.Mapper;

/**
 * 授权码(Code)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:22
 */
@Mapper
public interface CodeMapper extends BaseMapper<Code> {
}