package ly.message;
import ly.domain.Mission;
import ly.repository.MissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/*
 * 这个类的目的是为了防止mission发生不一致，所有对mission的处理都在这儿。
 */
@Component
public class MissionMessageQueue implements ApplicationListener<ContextRefreshedEvent>, Runnable {

  @Autowired
  MissionRepository missionRepository;

  Logger logger = LoggerFactory.getLogger(MissionMessageQueue.class);
  private final String  queueName = "FINAL_NAME";

  ExecutorService pool = Executors.newSingleThreadExecutor();
  LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();

  public boolean accept(Message message) {
    logger.info("i accpet" + message);
    messages.offer(message);
    return true;
  }

  /**  刷新mission数据 **/
  /**  分布式条件下，我们很难保证所有的数据都保持一致**/
  /**  只有写数据才会存在竞态条件，在写数据的时候保持数据一致性 **/
  /**  保证这个进程是单线程执行就能确保数据一致性 **/
  public  void flushDate() throws InterruptedException {
    Message message = messages.take();
    Mission selected = missionRepository.findOne(message.missionID);

    if(selected == null){
      logger.info("not find this messionId:" + message.toString());
    }else{
      message.cacl(selected);
      selected.setLastTriggerTime(new Date());
      missionRepository.save(selected);
    }
  }


  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    run();
    logger.info("start refresh Thread success!");
  }

  @Override
  public void run() {
    pool.execute(()->{
      try{
        while (true)
          flushDate();
      }
      catch (Exception e){
        logger.info("i am failed ,ready to restart");
        pool.execute(this);
      }
    });
  }
}
