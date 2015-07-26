package cn.terry.netty.frame_decoder.delimiterBasedFrameDecoder.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoServer {
	
	
	public void bind(int port){
		//建立线程组，用于接受客户端的连接
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		
		//建立线程组，用于进行SocketChannel的网络读写
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class) //创建的channel为NioServerSocketChannel类
				.option(ChannelOption.SO_BACKLOG, 1024)//设置tcp的参数，
				.childHandler(new ChildChannelHandler());//绑定i/o事件的处理类
		
			//绑定端口，调用同步阻塞方法sysn，等待绑定操作完成
			ChannelFuture f = b.bind(port).sync();
			
			System.out.println("time server is started");
			//调用sync阻塞方法，等待服务端链路关闭之后main函数才退出
			f.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	
	public static void main(String[] args) {
		new EchoServer().bind(8080);
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
			
			ch.pipeline()
			.addLast(new DelimiterBasedFrameDecoder(1024,delimiter))//增加专门对"$_"的为分隔符的解码器
			.addLast(new StringDecoder())//把收到的message对象，转换成string的解码器
			.addLast(new EchoServerHandler());
		}
		
	}
}
