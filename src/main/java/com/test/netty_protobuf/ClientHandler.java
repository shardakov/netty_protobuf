package com.test.netty_protobuf;

import com.protobuf.FileDisk;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;


import java.net.URI;
import java.util.Date;
import java.util.Random;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    static final String URL = System.getProperty("url", "http://127.0.0.1:8080/");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Connect：" + new Date());

        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();


        String path = uri.toASCIIString();


        //изначально подумывал сделать непрерывность отправки рандомных событий
        //через цикл, но затем отложил эту идею
        //while (true) {
            Random rand = new Random();
            int x = rand.nextInt(2);
            if (x == 0) {
                HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
                //Thread.sleep(10000);
                ctx.writeAndFlush(request);
                //System.out.println(request);
                System.out.println("Send Get request");


            } else if (x == 1) {
                HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, path);
                //Thread.sleep(10000);
                ctx.writeAndFlush(request);
                //System.out.println(request);
                System.out.println("Send Post request");


            } else {
                System.out.println("Bug");
            }
        }
    //}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //должен читать данные из канала, но я не смог додуматься, как это реализвать
        FileDisk.Disk disk = (FileDisk.Disk) msg ;
        System.out.println(msg);
        System.out.println(disk);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connection closed! ");
        super.channelInactive(ctx);
    }

}
