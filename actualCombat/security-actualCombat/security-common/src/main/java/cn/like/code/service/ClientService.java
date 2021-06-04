package cn.like.code.service;

import cn.like.code.base.BaseService;
import cn.like.code.entity.Client;
import cn.like.code.entity.dto.ClientDTO;

/**
 * 客户端(Client)表服务接口
 *
 * @author like
 * @since 2021-06-04 13:13:53
 */
public interface ClientService extends BaseService<Client> {

    ClientDTO getClient(String clientId);
}