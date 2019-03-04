package com.test.netty_protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private static final int CLIENT_PORT = 8080;


    public void run() throws Exception {
        System.out.println("WELCOME TO CLIENT");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());

            Channel ch;
            ch = b.connect("127.0.0.1",CLIENT_PORT).sync().channel();


            ch.closeFuture().sync();
        }
        finally{
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new NettyClient().run();
    }

}