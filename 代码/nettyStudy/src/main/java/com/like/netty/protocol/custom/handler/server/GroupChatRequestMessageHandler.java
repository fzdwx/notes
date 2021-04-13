package com.like.netty.protocol.custom.handler.server;

import com.like.netty.protocol.custom.message.chat.GroupChatRequestMessage;
import com.like.netty.protocol.custom.message.chat.GroupChatResponseMessage;
import com.like.netty.protocol.custom.server.session.GroupSession;
import com.like.netty.protocol.custom.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Create By like On 2021-04-13 10:40
 * <p>
 * 发送 群聊消息
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        final String groupName = msg.getGroupName();
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (!groupSession.hasGroup(groupName)) {
            ctx.writeAndFlush(new GroupChatResponseMessage(false, "不存在该聊天室"));
        } else {
            groupSession.getMembersChannels(groupName).forEach(ch -> {
                ch.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
            });
        }
    }
}
