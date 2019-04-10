package ly.schedule;


import ly.config.*;
import ly.message.ErrorMessage;
import ly.message.FinishMessage;
import ly.message.PublishMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = TopicRabbitConfig.BASIC_PUBLISH)
public class MyRabbitListener {

  @Autowired
  private AmqpTemplate rabbitTemplate;

  @RabbitHandler
  public void process(PublishMessage message) {
    System.out.println("fake recive: " + message);
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (new Date().getTime() % 2 == 0)
      this.rabbitTemplate.convertAndSend(TopicRabbitConfig.MISSION_PUBLISH_EXCHANGE,
          "basic.success", new FinishMessage(message));
    else
      this.rabbitTemplate.convertAndSend(TopicRabbitConfig.MISSION_PUBLISH_EXCHANGE,
          "basic.error", new ErrorMessage(message));
  }
}
