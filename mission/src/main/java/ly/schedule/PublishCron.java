package ly.schedule;


import ly.config.TopicRabbitConfig;
import ly.domain.Mission;
import ly.message.Message;
import ly.message.MissionMessageQueue;
import ly.message.PublishMessage;
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

  public static final String BASIC_PUBLISH = "basic.publish";


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
              Message message = new PublishMessage(mission.getId(),mission.getPublishPerTrigger());
              missionMessageQueue.accept(message);
              this.rabbitTemplate.convertAndSend(TopicRabbitConfig.MISSION_PUBLISH_EXCHANGE,
                  BASIC_PUBLISH, message);
            }
        );
  }


}
