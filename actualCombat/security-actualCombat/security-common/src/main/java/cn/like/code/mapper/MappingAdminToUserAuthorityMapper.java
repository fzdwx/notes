package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.MappingAdminToUserAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 用户和用户职权的映射表.(MappingAdminToUserAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:14:01
 */
@Mapper
public interface MappingAdminToUserAuthorityMapper extends BaseMapper<MappingAdminToUserAuthority> {

    /**
     * Description: 查询用户所对应的职权
     *
     * @param username username
     * @return java.util.Set<java.lang.String>
     * @author LiKe
     * @date 2020-06-22 11:24:59
     */
    Set<String> getUserAuthorities(String username);
}