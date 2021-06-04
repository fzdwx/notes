package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Approvals;
import org.apache.ibatis.annotations.Mapper;

/**
 * 批准的用户(Approvals)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:15
 */
@Mapper
public interface ApprovalsMapper extends BaseMapper<Approvals> {
}