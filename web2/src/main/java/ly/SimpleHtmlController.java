package ly;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ly.domain.User;
import org.apache.commons.io.IOUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.nio.ch.IOUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Controller
public class SimpleHtmlController {

  static String json = "";

  static {

    InputStream stencilsetStream = SimpleHtmlController.class.getClassLoader().getResourceAsStream("menu.json");
    try {
      json = IOUtils.toString(stencilsetStream, "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  @Autowired
  OAuth2RestTemplate oAuth2RestTemplate;

  @RequestMapping("/menu/getUserMenu")
  @ResponseBody
  public String getMenu() {
    //  return "{\"msg\":{\"id\":\"0\",\"text\":\"根节点\",\"state\":{\"opened\":true},\"checked\":true,\"children\":[{\"id\":\"1\",\"icon\":\"zmdi zmdi-settings\",\"text\":\"系统管理\",\"checked\":false,\"children\":[{\"id\":\"3\",\"icon\":\"\",\"url\":\"user\",\"text\":\"用户管理\",\"checked\":false,\"children\":[],\"parentId\":\"1\",\"hasParent\":true,\"hasChildren\":false}],\"parentId\":\"0\",\"hasParent\":false,\"hasChildren\":true},{\"id\":\"115\",\"icon\":\"zmdi zmdi-code-setting\",\"url\":\"\",\"text\":\"账号管理\",\"checked\":false,\"children\":[{\"id\":\"116\",\"icon\":\"\",\"url\":\"account/account\",\"text\":\"详细登录情况\",\"checked\":false,\"children\":[],\"parentId\":\"115\",\"hasParent\":true,\"hasChildren\":false},{\"id\":\"131\",\"icon\":\"\",\"url\":\"account/accountInfo\",\"text\":\"账号总览\",\"checked\":false,\"children\":[],\"parentId\":\"115\",\"hasParent\":true,\"hasChildren\":false}],\"parentId\":\"0\",\"hasParent\":false,\"hasChildren\":true},{\"id\":\"122\",\"icon\":\"\",\"url\":\"\",\"text\":\"视频管理\",\"checked\":false,\"children\":[{\"id\":\"123\",\"icon\":\"\",\"url\":\"video/upload\",\"text\":\"视频审核\",\"checked\":false,\"children\":[],\"parentId\":\"122\",\"hasParent\":true,\"hasChildren\":false},{\"id\":\"126\",\"icon\":\"\",\"url\":\"upload?type=bilibili\",\"text\":\"审核管理\",\"checked\":false,\"children\":[],\"parentId\":\"122\",\"hasParent\":true,\"hasChildren\":false},{\"id\":\"132\",\"icon\":\"zmdi zmdi-assignment-alert\",\"url\":\"mission/quickTitle\",\"text\":\"快速标题\",\"checked\":false,\"children\":[],\"parentId\":\"122\",\"hasParent\":true,\"hasChildren\":false}],\"parentId\":\"0\",\"hasParent\":false,\"hasChildren\":true},{\"id\":\"124\",\"icon\":\"\",\"url\":\"\",\"text\":\"爬虫\",\"checked\":false,\"children\":[{\"id\":\"125\",\"icon\":\"\",\"url\":\"mission/mission\",\"text\":\"爬虫url管理\",\"checked\":false,\"children\":[],\"parentId\":\"124\",\"hasParent\":true,\"hasChildren\":false}],\"parentId\":\"0\",\"hasParent\":false,\"hasChildren\":true}],\"parentId\":\"\",\"hasParent\":false,\"hasChildren\":true},\"code\":0}";
    return json;
  }

  @RequestMapping("/index")
  public Object index(ModelMap maps) {
    User user = oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);
    maps.put("user", user);
    return "index";
  }

  @RequestMapping("/")
  public Object index2(ModelMap maps) {
    User user = oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);
    maps.put("user", user);
    return "index";
  }

  @RequestMapping("/v1/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

    request.getSession().invalidate();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);

    }
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    SecurityContextHolder.getContext().setAuthentication(null);


    return "index";
  }

  @RequestMapping("/{path}")
  public Object simpleRoute(@PathVariable String path) {
    return path;
  }

  @RequestMapping("/mission/{path}")
  public Object simpelRoute2(@PathVariable String path) {
    return "mission/" + path;
  }

  @RequestMapping("/account/{path}")
  public Object simpelRoute3(@PathVariable String path) {
    return "account/" + path;
  }

  @RequestMapping("/video/{path}")
  public Object simpelRoute4(@PathVariable String path) {
    return "video/" + path;
  }

  @RequestMapping("/quickTitle/{path}")
  public Object simpelRoute5(@PathVariable String path) {
    return "quickTitle/" + path;
  }

}
