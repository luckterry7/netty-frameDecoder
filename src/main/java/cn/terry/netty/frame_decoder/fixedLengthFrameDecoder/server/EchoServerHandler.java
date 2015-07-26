package cn.terry.netty.frame_decoder.fixedLengthFrameDecoder.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter{


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		
		System.out.println("《FixdLengthFrameDecoder，length=20》The time server receive message :[" + msg + "];");
		
								
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将消息发送队列中的信息写到socketChannel中发送给客户端,
		//实际上，为了防止频繁的唤醒selector进行消息发送，write方法先把待发送的消息放入缓存数组中，最后调用flush，把信息全部写到socketChannel中
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
