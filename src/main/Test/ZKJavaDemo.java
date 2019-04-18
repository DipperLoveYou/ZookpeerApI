import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * fileName:
 * description:
 * author:zyq
 * createTime: 15:43
 */
public class ZKJavaDemo {
    private ZooKeeper zooKeeper = null;

    @Before
    public void init() throws Exception {
        //建立连接
        String conStr = "zk1:2181,zk2:2181,zk3:2181";
        //获取客户端链接对象
        zooKeeper = new ZooKeeper(conStr, 10000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("操作类型：" + watchedEvent.getType() + ",操作节点名称：" + watchedEvent.getPath());
                List<String> childrens = null;
                try {
                    childrens = zooKeeper.getChildren("/", true);
                    for (String children : childrens) {
                        System.out.println(children+"............");
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    //测试创建节点
    @Test
    public void testCreateNode() throws KeeperException, InterruptedException {
        //1 路径
        // 2，数据,getBytes(中文编码)
        // 3，权限 ACL Access Control Lists  world：默认方式，相当于全世界都能访问,所有客户端
        //    auth：不使用任何id，代表任何已确认用户
        //4,节点类型
        String s = zooKeeper.create ("/fffsss", "111".getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println ("结果：" + s);
    }


    //测试获取节点数据方法
    @Test
    public void testGetNodeData() throws KeeperException, InterruptedException {
      byte[] data = zooKeeper.getData ("/bbb", false, null);

        System.out.println (new String (data));
    }

    //测试获取节点孩子
    @Test
    public void testGetNodeChildren() throws KeeperException, InterruptedException {

        List <String> childrens = zooKeeper.getChildren ("/", true);

        for (String children : childrens) {
            System.out.println (children);
        }
        Thread.sleep (Long.MAX_VALUE);
    }

    //更新节点
    @Test
    public void testModifyNodeData() throws Exception {
        Stat stat = zooKeeper.setData ("/aaa", "中文会乱码吗".getBytes ("utf-8"), -1);
        if (stat != null) {
            System.out.println ("更新成功");

        } else {
            System.out.println ("更新失败");
        }
    }

    //删除节点
    @Test
    public void testDelNode() throws KeeperException, InterruptedException {


        zooKeeper.delete ("/aaa", -1);
    }

//判断节点是否存在
    @Test
    public void testNodeisExit()  throws KeeperException, InterruptedException{
   Stat stat=     zooKeeper.exists ("/dddd",false);
if(stat!=null){

    System.out.println ("存在");
}else{
    System.out.println ("不存在");
}
    }
}