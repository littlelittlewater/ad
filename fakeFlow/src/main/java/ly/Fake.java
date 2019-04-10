package ly;

import com.alibaba.fastjson.JSONObject;
import com.sun.jmx.snmp.tasks.ThreadService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.alibaba.fastjson.JSON.parse;

public class Fake {
  public static final int THREAD_SIZE = 600;
  private static AtomicInteger getAdd = new AtomicInteger(0);
  private static AtomicInteger extck = new AtomicInteger(0);
  private static AtomicInteger ctck = new AtomicInteger(0);
  Random random = new Random();
  CloseableHttpClient httpclient = HttpClientBuilder.create().build();
  public Fake() {
  }

  public JSONObject getAdJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("apiv", "1");
    jsonObject.put("sig", getSignature("DF436B45D28444E68D5E3CBFC58CE3D2", "415D6BE065B248ABAC867A3D61318534"));
    jsonObject.put("adid", "415D6BE065B248ABAC867A3D61318534");
    jsonObject.put("cip", "192.168.1.1");

    jsonObject.put("dt", 1);
    jsonObject.put("ost", 1);
    jsonObject.put("imei", "");
    jsonObject.put("osv", "4.0");
    jsonObject.put("userid", "3AF70327C5B25D12B1C56A86EA34938D");
    jsonObject.put("vendor", "jj");
    jsonObject.put("model", "XiaOMI");
    jsonObject.put("aid", "");

    jsonObject.put("connt", 4);
    jsonObject.put("ispt", 1);
    jsonObject.put("ua", "user-agent");
    return jsonObject;
  }

  public static String getSignature(String strAppKey, String strADID) {
    String sig = "";
    if (strAppKey.length() != 32 || strADID.length() != 32)
      return sig;
    try {
      byte[] btSrc = new byte[24];
      // Fill the first half of src bytes.
      {
        int len = strADID.length();
        byte[] data = new byte[len / 2 + (len % 2 == 0 ? 0 : 1)];
        for (int n = 0; n < data.length; n++) {
          String tmp = strADID.substring(n * 2, Math.min(n * 2 + 2, len));
          data[n] = (byte) Integer.parseInt(tmp, 16);
        }
        System.arraycopy(data, 0, btSrc, 0, 16);
      }
      // Fill the last half of src bytes.
      {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
        long min = cal.getTimeInMillis() / 1000 / 60;
        btSrc[16 + 0] = (byte) ((min & 0x00000000000000FFL) >>> 0);
        btSrc[16 + 1] = (byte) ((min & 0x000000000000FF00L) >>> 8);
        btSrc[16 + 2] = (byte) ((min & 0x0000000000FF0000L) >>> 16);
        btSrc[16 + 3] = (byte) ((min & 0x00000000FF000000L) >>> 24);
        btSrc[16 + 4] = (byte) ((min & 0x000000FF00000000L) >>> 32);
        btSrc[16 + 5] = (byte) ((min & 0x0000FF0000000000L) >>> 40);
        btSrc[16 + 6] = (byte) ((min & 0x00FF000000000000L) >>> 48);
        btSrc[16 + 7] = (byte) ((min & 0xFF00000000000000L) >>> 56);
      }
      // Generate result bytes.
      byte[] btResult;
      {
        byte[] key;
        {
          int len = strAppKey.length();
          key = new byte[len / 2 + (len % 2 == 0 ? 0 : 1)];
          for (int n = 0; n < key.length; n++) {
            String t = strAppKey.substring(n * 2, Math.min(n * 2 + 2, len));
            key[n] = (byte) Integer.parseInt(t, 16);

          }
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(key, "HmacSHA1"));
        btResult = mac.doFinal(btSrc);
      }
      // Translate to string.
      sig = String.format("%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
          btResult[0], btResult[1], btResult[2], btResult[3], btResult[4],
          btResult[5], btResult[6], btResult[7], btResult[8], btResult[9],
          btResult[10], btResult[11], btResult[12], btResult[13], btResult[14],
          btResult[15], btResult[16], btResult[17], btResult[18], btResult[19]);
    } catch (Throwable t) {
    }
    return sig;
  }

  public  JSONObject doPost(String url, JSONObject json) {


    HttpPost post = new HttpPost(url);
    JSONObject response = null;
    try {
      StringEntity s = new StringEntity(json.toString());
      s.setContentEncoding("UTF-8");
      s.setContentType("application/json");//发送json数据需要设置contentType
      post.setEntity(s);
      HttpResponse res;
      res = httpclient.execute(post);
      if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
        response = (JSONObject) parse(result);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return response;
  }

  public  JSONObject doGet(String url) {


    HttpGet post = new HttpGet(url);
    JSONObject response = null;
    try {

      HttpResponse res;
      res = httpclient.execute(post);
      if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
        response = (JSONObject) parse(result);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return response;
  }

  public  void main(String[] args) {
    ExecutorService cachedThreadPool = Executors.newFixedThreadPool(THREAD_SIZE+1);

    Stream.iterate(0, i -> i + 1).limit(THREAD_SIZE).forEach(i -> {

      cachedThreadPool.execute(() -> {
        new Fake().shua();
      });
    });

    cachedThreadPool.execute(() -> {
      while (true) {
        System.out.println("获取广告次数:" + getAdd.get() + "曝光数目" + extck.get()
            + "点击数目" + ctck.get());
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    });
  }


  public void shua() {
    while (true) {
      try {

        JSONObject getAdd = new Fake().getAdJson();
        getAdd = doPost("http://api.helloadx.com/api/getad", getAdd);
        if (getAdd.getString("c").equals("0"))
          Fake.getAdd.incrementAndGet();
        //  System.out.println(getAdd);


        if (random.nextInt(10) != 0) {
          JSONObject getExtck = doGet(getAdd.getJSONArray("extck").getString(0));
          //   System.out.println(getExtck);
          if (getExtck.getString("c").equals("0"))
            Fake.extck.incrementAndGet();
        }


        if (random.nextInt(10) == 0) {
          JSONObject getEtck = doGet(getAdd.getJSONArray("ctck").getString(0));
          //    System.out.println(getEtck);
          if (getEtck.getString("c").equals("0"))
            Fake.ctck.incrementAndGet();
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }

  }
}
