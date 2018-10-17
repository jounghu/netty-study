#### Netty 学习


##### 1. Netty Server启动

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


##### 1. Netty Client启动

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
