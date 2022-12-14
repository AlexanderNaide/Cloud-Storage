package com.gb;

import com.gb.handlers.CloudServerHandler;
import com.gb.handlers.CloudServerHandlerDB;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerStart {

    static final int MAX_OBJ_SIZE = 1024 * 1024 * 100;

    public static void main(String[] args) {
        OperatorBD.clearAllConnects();
        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            ChannelFuture future = bootstrap.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
//                                    new CloudServerHandler()
                                    new CloudServerHandlerDB()
                            );
                        }
                    }).bind(6830).sync();
            log.debug("Server started...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.err.println("Что-то случилось");
            log.error("e=", e);
        } finally {
            OperatorBD.clearAllConnects();
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
