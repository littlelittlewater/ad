package ly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableResourceServer
@EnableOAuth2Client
@EnableHystrix

public class FakeApplication {
  public static void main(String[] args) {
    SpringApplication.run(FakeApplication.class, args);
  }

  @LoadBalanced
  @Bean
  public OAuth2RestTemplate loadBalancedRestTemplate(
      OAuth2ProtectedResourceDetails details, OAuth2ClientContext context) {
    return new OAuth2RestTemplate(details, context);
  }

}
