package cn.like.code.config.support.custom.client;

import cn.like.code.entity.dto.ClientDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义的 {@link ClientDetails}
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-06-15 13:07
 */
public class CustomClientDetails implements ClientDetails {

    private final ClientDTO clientDto;

    public CustomClientDetails(ClientDTO clientDto) {
        this.clientDto = clientDto;
    }

    private static Set<String> composeFrom(String raw) {
        return Arrays.stream(StringUtils.split(raw, ","))
                     .map(StringUtils::trimToEmpty).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }

    @Override
    public String getClientId() {
        return clientDto.getId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clientDto.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientDto.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return composeFrom(clientDto.getScope());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return composeFrom(clientDto.getAuthorizedGrantType());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return composeFrom(clientDto.getRedirectUri());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return clientDto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDto.getAccessTokenValidity();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDto.getRefreshTokenValidity();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return clientDto.isAutoApprove();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}