package com.aaa.zk.dp;

import com.aaa.zk.util.ZKCUtil;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * fileName:FtpClients
 * description:
 * author:zz
 * createTime:2019/4/15 15:18
 */
public class FtpClients {

    /**
     * 获取
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void showChildren() throws KeeperException, InterruptedException{
        //获取客户端
        ZooKeeper zooKeeper = ZKCUtil.initZkClient();
        //获取/zkc下的所有子节点，获取节点中的值，就是注册 ftp服务器的名称
        List<String> children = zooKeeper.getChildren("/zkc", true);
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
        System.out.println("调用的ftp服务器集群一共有："+ftpNames+"在工作。。。。");
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        showChildren();
        //一直运行监控ftp服务器情况
        Thread.sleep(Integer.MAX_VALUE);
    }
}
