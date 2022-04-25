package com.learning.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoServerClient {
    
    private final String host;
    
    private final int port;
    
    private final int sendNumber;
    
    
    public EchoServerClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }
    
    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<ServerSocketChannel>() {
                    @Override
                    protected void initChannel(ServerSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                        socketChannel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                        socketChannel.pipeline().addLast(new EchoClientHandler(sendNumber));
                    }
                });
        try{
            ChannelFuture channelFuture = bootstrap.localAddress(host, port).bind().sync();
            System.out.println("Http Server started, Listening on " + port);
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8080, 3).run();
    }
}
