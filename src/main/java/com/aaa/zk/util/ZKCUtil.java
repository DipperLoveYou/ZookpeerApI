package com.aaa.zk.util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * fileName:ZKCUtil
 * description:
 * author:zz
 * createTime:2019/4/15 15:19
 */
public class ZKCUtil {
    private static   ZooKeeper zooKeeper = null;
    /**
     * 初始化客户端
     * @return
     */
    public static   ZooKeeper initZkClient()  {
        try {
            zooKeeper = new ZooKeeper("zk1:2181,zk2:2181,zk3:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("事件类型："+watchedEvent.getType()+",操作节点："+watchedEvent.getPath());

                    //获取/zkc下的所有子节点，获取节点中的值，就是注册 ftp服务器的名称
                    List<String> children = null;
                    try {
                        children = zooKeeper.getChildren("/zkc", true);
                        //声明节点数组
                        List<String> ftpNames = new ArrayList<String>();
                        //遍历结果，加入节点数组
                        for (String path : children) {
                            //根据节点路径获取节点数据
                            byte[] data = zooKeeper.getData("/zkc/" + path, false, null);
                            String ftpName = new String(data);
                            ftpNames.add(ftpName);
                        }
                        //模拟客户的工作情况
                        System.out.println("客户端开始调用ftp提供的接口，执行上传下载方法。。。。。。。。。。");
                        //打印集群情况
                        System.out.println("调用的ftp服务器集群111一共有："+ftpNames+"在工作。。。。");
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
