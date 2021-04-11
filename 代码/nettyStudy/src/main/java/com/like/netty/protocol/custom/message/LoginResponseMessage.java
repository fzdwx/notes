package com.like.netty.protocol.custom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage {
    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
