package ly.message;

import ly.domain.Mission;

/*发布的message*/
public class PublishMessage extends Message {
  public PublishMessage(Long missionID, Long missionNumber) {
    super(missionID,missionNumber);
  }

  @Override
  public void cacl(Mission selected) {
    logger.info("publish message is calcing");
    selected.changePublishNumber(number);
  }

  public PublishMessage(Message message) {
    super(message);
  }

  @Override
  public String getName() {
    return "publish";
  }

  public PublishMessage() {
  }
}