package ly.message;

import ly.domain.Mission;

/*发布的message*/
public class PublishMessage extends Message {
  @Override
  public void cacl(Mission selected) {
    logger.info("publish message is calcing");
    selected.changePublishNumber(number);
  }

  public PublishMessage(Long missionID, Long number) {
    super(missionID, number);
  }

  public PublishMessage() {
  }
}