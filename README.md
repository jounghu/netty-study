### Netty 学习


#### 1. Netty Server启动

```java
        // 负责监听端口, accept新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 负责处理连接读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                // 指定NIO线程模型
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                    }
                });
        int initPort = 8000;
        bindPort(serverBootstrap, initPort);
```

总结Netty Server流程:

1. 起两个工作线程组,boosWorker 和 childWorker, boosWorker负责接收线程,处理新来的连接。 childWorker负责channel和handler的读写流程
2. 指定线程模型，NioSocketChannel
3. 指定Handler处理方式
4. 绑定端口号

#### 2. Netty Client启动

```java
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                // 工作线程
                .group(workGroup)
                // 工作线程模型
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });
        // 客户端连接回调
        bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                log.info("Client connect server success , server url:port {}:{}", "localhost", "8000");
            } else {
                log.error("Client connect server failed");
            }
        });
```
总结NettyClient启动流程:
1. 起一个Worker线程组
2. 指定线程模型
3. 指定读写Handler
4. 连接netty 服务器

#### 3. Netty Client 与 Netty Server简单通信

```java
    @Slf4j
    public class MessageHandler extends ChannelInboundHandlerAdapter {
    
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            log.info("收到消息: {}", byteBuf.toString(StandardCharsets.UTF_8));
            // 同时回复消息
            ByteBuf buffer = ctx.channel().alloc().buffer();
            buffer.writeBytes("你好，客户端，我是服务器1".getBytes(StandardCharsets.UTF_8));
            ctx.channel().writeAndFlush(buffer);
        }
    }
```

总结: 

1. 消息都通过ByteBuf这个数据结构来构造发送和读取
2. 消息最好指定编码类型，不然无法序列化


#### Netty ByteBuf 数据载体详解

![image](https://user-gold-cdn.xitu.io/2018/8/5/1650817a1455afbb?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

ByteBuf包含:

readIndex: 读指针索引位置

writeIndex: 写指针索引位置

capacity： 容量

maxCapacity: 最大容量

每读完一个字节 readIndex++

每写入一个字节 writeIndex++

当writeIndex == capacity时候，那么进行扩容

当 capacity == maxCapacity 时候不可扩容，同时无法写入



