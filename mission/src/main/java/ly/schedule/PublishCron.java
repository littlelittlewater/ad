package ly.schedule;


import ly.config.TopicRabbitConfig;
import ly.domain.Mission;
import ly.message.FinishMessage;
import ly.message.Message;
import ly.message.MissionMessageQueue;
import ly.repository.MissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 从db获取数据来发布
 */
@Component
public class PublishCron {
  @Autowired
  MissionRepository missionRepository;

  @Autowired
  MissionMessageQueue missionMessageQueue;

  @Autowired
  private AmqpTemplate rabbitTemplate;

  Logger logger = LoggerFactory.getLogger(PublishCron.class);

  /* 定时检查是否有可以触发的任务*/
  @Scheduled(cron = "* * * * * *")
  public void publish() {
    logger.info("publishCron is starting");
    List<Mission> needToDo = missionRepository.findByCancelEquals(false);
    needToDo.stream()
        .filter(mission -> mission.canPublish())
        .forEach(mission -> {
              Message message = Message.newPublishMessage(mission);
              missionMessageQueue.accept(message);
              this.rabbitTemplate.convertAndSend("topicExchange",
                  TopicRabbitConfig.message, message);
            }
        );
  }

  /**
   * 模拟消费请求
   **/
  @Scheduled(cron = "* * 3 * * *")
  public void cosume() {
    logger.info("publishCron is starting");
    missionMessageQueue.accept(new FinishMessage(1L, 1L));
  }
}
