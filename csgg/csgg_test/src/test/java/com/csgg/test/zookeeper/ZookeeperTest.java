package com.csgg.test.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTest {

	private static String ADDR = "localhost:2181";
	private  static int TIMEOUT = 5000;
	private static CountDownLatch c = new CountDownLatch(1);
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		try {
			ZooKeeper zk = new ZooKeeper(ADDR, TIMEOUT, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					Event.KeeperState st = event.getState();
					if (st == Event.KeeperState.SyncConnected) {
						System.out.println("链接成功");
						c.countDown();
					}
				}
				
			});
			
			System.out.println("链接中...");
			c.await();
			
			String s = zk.create("/wl", "abcd".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("创建成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
