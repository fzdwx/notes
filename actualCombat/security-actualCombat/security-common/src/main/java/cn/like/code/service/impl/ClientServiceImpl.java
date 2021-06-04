package cn.like.code.service.impl;

import cn.like.code.entity.Client;
import cn.like.code.entity.dto.ClientDTO;
import cn.like.code.mapper.ClientMapper;
import cn.like.code.mapper.MappingClientToClientAuthorityMapper;
import cn.like.code.mapper.MappingClientToResourceServerMapper;
import cn.like.code.service.ClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端(Client)表服务实现类
 *
 * @author like
 * @since 2021-06-04 13:13:53
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private MappingClientToClientAuthorityMapper mappingClientToClientAuthorityMapper;
    @Autowired
    private MappingClientToResourceServerMapper mappingClientToResourceServerMapper;

    @Override
    public ClientDTO getClient(String clientId) {
        final ClientDTO dto = getBaseMapper().getClient(clientId);
        dto.setAuthorities(mappingClientToClientAuthorityMapper.queryClientAuthorities(dto.getClientId()));
        dto.setResourceIds(mappingClientToResourceServerMapper.queryResourceServerIds(dto.getClientId()));
        return dto;
    }
}