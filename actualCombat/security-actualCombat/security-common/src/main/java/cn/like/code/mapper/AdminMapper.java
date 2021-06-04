package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Admin;
import cn.like.code.entity.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 后台用户表(Admin)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:13
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * Description: 通过用户 ID 获取用户对象
     *
     * @param username 用户名称
     * @return admin
     * @author like
     * @date 2020-06-22 10:49:39
     */
    AdminDTO getAdmin(@Param("username") String username);
}