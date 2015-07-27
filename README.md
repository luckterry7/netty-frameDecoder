# netty-frameDecoder
换行符，自定义分隔符，定长度解码器
# 说明
使用tcp传送数据，由于缓存区大小的设置，MSS的tcp分段等因素，数据传输时会出现TCP粘包/拆包的问题。<br>
但是底层的tcp无法理解上层的业务数据，所以在底层也无法保证数据包拆分和重组。<br>
业界的主流处理方法有如下<br>
1，消息定长。<br>
2，在包尾增加回车换行符进行分割，例如FTP协议<br>
3，将消息分成消息头和消息体，消息头表示消息总长度（或消息长度）<br>


例子中的LineBasedFrameDecoder，是netty提供专门用于处理换行符分割的解码器<br>
DelimiterBasedFrameDecoder，是用户自定义分隔符的解码器<br>
FixedLengthFrameDecoder，是设定定长的解码器
