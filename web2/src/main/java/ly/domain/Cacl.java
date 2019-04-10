package ly.domain;

import static java.lang.Math.min;

public class Cacl {
  public static String[] desc = new String[]{"低于50%", "低于100%", "低于300%",
      "低于500%", "高于500%"};
  public static double[] jieduan = new double[]{0.5 * 0.01, 0.75 * 0.01, 1 * 0.01,
      1.5 * 0.01, 2 * 0.01};
  public static double[] xishu = new double[]{0, 0.5, 1,
      3, 5, 10000
  };

 /* public static void main(String[] args) {
    *//*double[] moneys = new double[]{50,100,300,500};
    double[] have = new double[]{40,80,100,150};
    for(double d :have)
      for (double m : moneys)
        cacl(m,d);
    *//*
    cacl(300,150);
  }*/

  public static void cacl(double number, double have) {
    System.out.println("-----------------------------------");
    System.out.println("当前金额:" + have + "消耗金额:" + number);
    number -= have;
    double decrement = 0;
    for (int i = 0; i < desc.length; i++) {
      double willCacl = (number > have * xishu[i])
          ? (min(have * xishu[i + 1], number - have * xishu[i])) : 0;
      double now = willCacl * jieduan[i];
      System.out.println(desc[i] +
          ":计算部分:" + willCacl +
          ",计算比例:" + jieduan[i]
          + ",扣除数额:" + now);
      decrement += now;
    }
    System.out.println("最终结果为：[" + (have - decrement) + "],取整后为:" +
        Math.floor(have - decrement));
  }
}
