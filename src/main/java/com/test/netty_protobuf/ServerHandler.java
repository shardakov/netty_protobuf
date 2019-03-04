package com.test.netty_protobuf;


import com.protobuf.FileDisk;
import com.protobuf.FileDisk.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.util.Random;


public class ServerHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connected client address:" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //определяет тип входящего запроса
        if (HttpMethod.GET.equals(((DefaultHttpRequest) msg).method())) {
            System.out.println("Get test request");
            ctx.writeAndFlush(randomPesponse());

        } else if (HttpMethod.POST.equals(((DefaultHttpRequest) msg).method())) {
            System.out.println("POST test request");
            ctx.writeAndFlush(randomPesponse());

        } else {
            System.out.println("now now");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws InterruptedException {
        ctx.flush();
    }
    // метод, который формирует протобаф сущность
    private FileDisk.Disk randomPesponse() throws InterruptedException {

        Random random = new Random();
        int x = random.nextInt(2);
        if (x == 0) {
            Thread.sleep(50);
            FileDisk.Disk disk = FileDisk.Disk.newBuilder()
                    .setVendor("HP")
                    .addDiskSpec(FileDisk.DiskSpec.newBuilder()
                            .setId(1)
                            .setVolume(1024)
                            .setWSpeed(512)
                            .setRSpeed(256)
                            .setType(DiskSpec.Type.HDD)
                            .build())
                    .addDiskSpec(FileDisk.DiskSpec.newBuilder()
                            .setId(2)
                            .setVolume(2048)
                            .setWSpeed(1024)
                            .setRSpeed(512)
                            .setType(DiskSpec.Type.SSD)
                            .build())
                    .build();


            return disk;

        } else if (x == 1) {
            FileDisk.Disk disk = FileDisk.Disk.newBuilder()
                    .setVendor("Huawei")
                    .addDiskSpec(FileDisk.DiskSpec.newBuilder()
                            .setId(1)
                            .setVolume(5120)
                            .setWSpeed(1024)
                            .setRSpeed(756)
                            .setType(DiskSpec.Type.SSD)
                            .build())
                    .addDiskSpec(FileDisk.DiskSpec.newBuilder()
                            .setId(2)
                            .setVolume(2048)
                            .setWSpeed(1024)
                            .setRSpeed(512)
                            .setType(DiskSpec.Type.SAS)
                            .build())
                    .build();


            return disk;
        }
        return null;
    }
}
