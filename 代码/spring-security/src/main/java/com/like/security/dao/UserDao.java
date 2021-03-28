package com.like.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.like.security.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-28 17:05
 */
@Mapper
public interface UserDao extends BaseMapper<Users> {

}
