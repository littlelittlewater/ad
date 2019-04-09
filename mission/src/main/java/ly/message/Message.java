package ly.message;

import ly.domain.Mission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * 服务器发布messionID 让客户端处理
 *
 */
public abstract class Message {
  static Logger logger = LoggerFactory.getLogger(Message.class);

  public Long missionID;
  public Long number;

  public Message() {
  }

  public static PublishMessage newPublishMessage(Mission message) {
    return new PublishMessage(message.getId(), message.getRealPublishNumber());
  }


  public abstract void cacl(Mission selected);

  public Message(Long missionID, Long number) {
    this.missionID = missionID;
    this.number = number;
  }

  @Override
  public String toString() {
    return "Message{" +
        "missionID=" + missionID +
        ", number=" + number +
        '}';
  }

}