package ly.schedule;


import ly.config.TopicRabbitConfig;
import ly.message.FinishMessage;
import ly.message.Message;
import ly.message.MissionMessageQueue;
import ly.message.PublishMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = TopicRabbitConfig.message)
public class MyRabbitListener {

  @Autowired
  MissionMessageQueue missionMessageQueue;

  @RabbitHandler
  public void process(PublishMessage message) {
    missionMessageQueue.accept(new FinishMessage(message));
    System.out.println("basic.add: " + message);
  }

  public void process(FinishMessage message) {
    missionMessageQueue.accept(message);
    System.out.println("basic.add: " + message);
  }
}
