package ly.message;

import ly.domain.Mission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;


/**
 * 服务器发布messionID 让客户端处理
 */
public abstract class Message {
  static Logger logger = LoggerFactory.getLogger(Message.class);

  public Long missionID;
  public Long number;
  public String uuid;
  public Date createTime;

  public Long getMissionID() {
    return missionID;
  }

  public void setMissionID(Long missionID) {
    this.missionID = missionID;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }


  public Message() {
    uuid = "" + new Date().getTime() + UUID.randomUUID();
    createTime = new Date();
  }

  public abstract void cacl(Mission selected);

  public Message(Message message) {

    this.missionID = message.missionID;
    this.number =  message.number;
    uuid =  message.uuid;
    createTime = message.createTime;
  }

  @Override
  public String toString() {
    return "Message{" +
        "missionID=" + missionID +
        ", number=" + number +
        '}';
  }

  public abstract String getName();
}