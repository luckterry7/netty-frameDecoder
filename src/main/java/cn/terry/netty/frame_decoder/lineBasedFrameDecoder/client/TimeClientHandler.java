package cn.terry.netty.frame_decoder.lineBasedFrameDecoder.client;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	private int counter;
	private byte[] req;

	
	
	public TimeClientHandler() {
		req = ("query time" + System.getProperty("line.separator")).getBytes();
	}

	
	/**
	 * 当客户端和服务端tcp连接成功，会调用该方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for(int i=0; i<100;i++){
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] resp = new byte[buf.readableBytes()];
//		buf.readBytes(resp);
		
//		String body = new String(resp,"UTF-8");
		String body = (String) msg;
		System.out.println("now is :" + body + ";counter is :" + ++counter);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());;
		ctx.close();
	}
	
}
