package com.csgg.test.zookeeper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ZookeeperACLTest {

	private static String ADDR = "localhost:2181";
	private  static int TIMEOUT = 5000;
	private static CountDownLatch c = new CountDownLatch(1);
	
	public static void main(String[] args) throws KeeperException, InterruptedException, NoSuchAlgorithmException {
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
			
			Id id1 = new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin123"));
			ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
			
			Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("guest:guest123"));
			ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);
			
			List<ACL> list = new ArrayList<ACL>();
			list.add(acl1);
			list.add(acl2);
			
			String s = zk.create("/wlACL", "abcd".getBytes(), list, CreateMode.PERSISTENT);
			System.out.println("创建成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
