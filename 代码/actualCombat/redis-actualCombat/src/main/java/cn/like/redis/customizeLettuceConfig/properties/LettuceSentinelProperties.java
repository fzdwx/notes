package cn.like.redis.customizeLettuceConfig.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LettuceSentinelProperties extends LettuceSingleProperties {
 
    private String masterId;
}
