package com.like.netty.protocol.custom.handler.server;

import cn.hutool.core.util.ObjectUtil;
import com.like.netty.protocol.custom.message.chat.GroupCreateRequestMessage;
import com.like.netty.protocol.custom.message.chat.GroupCreateResponseMessage;
import com.like.netty.protocol.custom.server.session.GroupSession;
import com.like.netty.protocol.custom.server.session.factory.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * Create By like On 2021-04-12 20:22
 * <p>
 * 创建群
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        final String groupName = msg.getGroupName();
        final Set<String> members = msg.getMembers();
        final String creator = msg.getCreator();
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();

        if (!ObjectUtil.isNull(groupSession.createGroup(groupName, members, creator))) {
            // 创建失败
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建失败！！！"));
        } else {
            // 创建成功
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功！"));
            final List<Channel> membersChannels = groupSession.getMembersChannels(groupName);
            membersChannels.forEach(channel -> {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入:" + groupName + "，群主:" + creator));
            });
        }
    }
}
