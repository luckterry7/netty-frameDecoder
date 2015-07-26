package cn.terry.netty.frame_decoder.delimiterBasedFrameDecoder.client;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter{
	private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
	private int counter;
	static final String REQ = "hi,terry.Welcome to netty.$_";

	
	
	public EchoClientHandler() {
	}

	
	/**
	 * 当客户端和服务端tcp连接成功，会调用该方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for(int i=0; i<100;i++){
			message = Unpooled.copiedBuffer(REQ.getBytes());
			ctx.writeAndFlush(message);
		}
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		System.out.println("resp is [:" + body + "];counter is :" + ++counter);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());;
		ctx.close();
	}
	
}
