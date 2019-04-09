package ly.controller;

import ly.message.FinishMessage;
import ly.message.MissionMessageQueue;
import ly.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MissionController {

  @Autowired
  private MissionRepository missionRepository;

  @Autowired
  MissionMessageQueue missionMessageQueue;


  @RequestMapping("/finish")
  /**
   * 这儿必须要排队，因为在高并发请求下对统一资源的访问可能出现静态问题
   */
  public ResponseEntity<Boolean> finished(@RequestBody FinishMessage message){
    return Optional.ofNullable(missionMessageQueue.accept(message))
        .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
