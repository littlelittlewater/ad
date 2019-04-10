package ly.schedule;


import ly.config.TopicRabbitConfig;
import ly.message.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class MyRabbitListener {

  @Autowired
  MissionMessageQueue missionMessageQueue;

  @RabbitListener(queues = TopicRabbitConfig.PUBLISH_SUCCESS)
  public void process(FinishMessage message) {
    missionMessageQueue.accept(message);
    System.out.println("finish message: " + message);
  }

  @RabbitListener(queues = TopicRabbitConfig.PUBLISH_ERROR)
  public void  handlerErro(ErrorMessage message) {
    missionMessageQueue.accept(message);
    System.out.println("error message: " + message);
  }
}
