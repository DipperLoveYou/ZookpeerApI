package com.aaa.zk.dp;

import com.aaa.zk.util.ZKCUtil;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * fileName:FtpServers
 * description:
 * author:zz
 * createTime:2019/4/15 14:42
 */
public class FtpServers {


    public static void main(String[] args) throws KeeperException, InterruptedException {
        //获取客户端
        ZooKeeper zooKeeper = ZKCUtil.initZkClient();
        //判断父节点是否存在，不存在，创建
        Stat stat = zooKeeper.exists("/zkc", false);
        if(stat==null){//不存在，创建
            zooKeeper.create("/zkc","zkc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
         //args = new String[]{"ftp6"};
        // fpt服务节点向zookeeper cluster 注册 （创建节点  ephameral 临时的  sequential有序的）
        zooKeeper.create("/zkc/ftp",args[0].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //模拟ftp服务器功能
        System.out.println("开始工作，上传...下载文件。。。。。。。。。。。。。。");
        //为了不让session丢失，让该节点一直运行
        Thread.sleep(Integer.MAX_VALUE);
    }
}
