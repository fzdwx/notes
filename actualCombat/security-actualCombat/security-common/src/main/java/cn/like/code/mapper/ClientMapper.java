package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Client;
import cn.like.code.entity.dto.ClientDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户端(Client)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:54
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {

    /**
     * Description: 通过客户端 ID 获取客户端
     *
     * @param id 客户端 id
     * @return {@link Client}
     * @author LiKe
     * @date 2020-06-15 13:17:53
     */
    ClientDTO getClient(@Param("id") String id);
}