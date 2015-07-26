package cn.terry.netty.frame_decoder.delimiterBasedFrameDecoder.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
	public void connect(String host,int port){
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){
				
				@Override
				protected void initChannel(SocketChannel ch)
						throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					
					ch.pipeline()
					.addLast(new DelimiterBasedFrameDecoder(1024,delimiter))//增加专门对"$_"的为分隔符的解码器
					.addLast(new StringDecoder())//把收到的message对象，转换成string的解码器
					.addLast(new EchoClientHandler());
				}
			});
			//绑定端口，调用同步阻塞方法sysn，等待绑定操作完成
			ChannelFuture f = b.connect(host,port).sync();
			
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
	
	}
	
	public static void main(String[] args) {
		new EchoClient().connect("127.0.0.1", 8080);
	}
}
