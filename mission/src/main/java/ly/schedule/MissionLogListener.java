package ly.schedule;
import ly.config.TopicRabbitConfig;
import ly.domain.MissionLog;
import ly.message.Message;
import ly.repository.MissionLogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissionLogListener {

  @Autowired
  MissionLogRepository missionLogRepository;

  @RabbitListener(queues = TopicRabbitConfig.MISSION_ALL)
  public void  handlerErro(Message message) {
    System.out.println("log message: " + message);
    missionLogRepository.save(MissionLog.build(message));
  }
}
