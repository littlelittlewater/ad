package ly;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyRedisTest {

  MyRedis myRedis = null;
  @Before
  public void init(){
    myRedis = new MyRedis();
  }
  @Test
  public void testSout(){
    long sTime = System.currentTimeMillis();

    long eTime = System.currentTimeMillis();
    System.out.println("sout" + (eTime-sTime));


  }
}