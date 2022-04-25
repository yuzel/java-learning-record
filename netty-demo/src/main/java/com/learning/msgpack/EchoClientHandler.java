package com.learning.msgpack;

import com.learning.vo.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    
    private final int sendNumber;
    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }
    
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = userInfo();
        for(UserInfo userInfo : userInfos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }
    
    private UserInfo[] userInfo() {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG -----》" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgpack message:" + msg);
        ctx.write(msg);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
