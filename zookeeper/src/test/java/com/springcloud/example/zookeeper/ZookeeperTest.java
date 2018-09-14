package com.springcloud.example.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/14 17:07
 */
@Slf4j
public class ZookeeperTest {
    private static final String CONNECTION_ADDRESS = "192.168.86.133:2181";
    private static final Integer SESSION_TIMEOUT = 5000;
    private static CountDownLatch latch = new CountDownLatch(1);
    @Test
    public void test1() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTION_ADDRESS, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected){
                    latch.countDown();
                }
            }
        });

        latch.await();
        // 获取ZooKeeper客户端对象
        log.info(zooKeeper.toString());
    }
}
