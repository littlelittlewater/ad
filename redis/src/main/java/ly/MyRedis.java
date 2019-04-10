package ly;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class MyRedis {
  private JedisCluster jedisCluster;
  private byte[][][] input = new byte[2][10 * 100000 + 1][];
  private  byte[] enmpty = new byte[4096];
  private void init() {
    Set<HostAndPort> nodes = new HashSet<>();
    String ip = "127.0.0.1";
    nodes.add(new HostAndPort(ip, 7101));
    nodes.add(new HostAndPort(ip, 7102));
    nodes.add(new HostAndPort(ip, 7100));
    nodes.add(new HostAndPort(ip, 7103));
    nodes.add(new HostAndPort(ip, 7104));
    jedisCluster = new JedisCluster(nodes);

    for (int i = 0; i < 100 * 10000; i++) {
      input[0][i] = intToByte4(i);
      input[1][i] = enmpty;
    }
  }

  public static byte[] intToByte4(int i) {
    byte[] targets = new byte[4];
    targets[3] = (byte) (i & 0xFF);
    targets[2] = (byte) (i >> 8 & 0xFF);
    targets[1] = (byte) (i >> 16 & 0xFF);
    targets[0] = (byte) (i >> 24 & 0xFF);
    return targets;
  }

  public static void main(String[] args) {
    MyRedis myRedis = new MyRedis();
    myRedis.test();
  }

  /**
   * 10000条数据
   * redis集群数目  存取大小  读(平均值)       写(平均值)
   * 3              4K       980             1600
   * 4              4K       1013            1300
   * 5              4K       918             1250
   * 6              4K       829             1082
   * 7              4k       854             1019
   */
  private void test() {
    int times = 10000;
    System.out.println("donothing:  " + cacl(times, new DoNothing()));
    // System.out.println("Sout:  " + cacl(times, new Sout()));
    System.out.println("write:" + cacl(times, new Write()));
    System.out.println("read:" + cacl(times, new Read()));

  }

  public MyRedis() {
    init();
  }

  public long cacl(int number, SomeThing someThing) {
    long sTime = System.currentTimeMillis();
    for (int i = 0; i < 10; i++)
      Stream.iterate(0+i*100000, n -> n + 1).limit(number).forEach(someThing::invoke);
    long dsTime = System.currentTimeMillis();
    return (dsTime - sTime)/10;
  }

  public class DoNothing implements SomeThing {

    @Override
    public void invoke(int k) {
    }
  }

  public class Sout implements SomeThing {

    @Override
    public void invoke(int k) {
      System.out.println(k);
    }
  }

  public interface SomeThing {
    public void invoke(int k);
  }

  private class Write implements SomeThing {
    @Override
    public void invoke(int k) {
      jedisCluster.set(input[0][k], input[1][k]);
    }
  }

  private class Read implements SomeThing {
    @Override
    public void invoke(int k) {
      jedisCluster.get(input[0][k]);
    }
  }
}
