package ly;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableOAuth2Sso
@EnableZuulProxy
public class WebApplication extends WebSecurityConfigurerAdapter {
  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/**").authorizeRequests()
        .antMatchers("/", "/index.html", "/assets/**", "/login","/logout",
            "/api/catalog/**").permitAll().anyRequest().authenticated()
        .and().logout().deleteCookies().clearAuthentication(true).invalidateHttpSession(true)
        .logoutUrl("/logout").logoutSuccessUrl("http://127.0.0.1:8001/auth/logout").permitAll()
        .and().headers().frameOptions().disable().and().csrf().disable(); // 这样虽然可以工作，但不安全
  }

  @LoadBalanced
  @Bean
  public OAuth2RestTemplate loadBalancedRestTemplate(
      OAuth2ProtectedResourceDetails details, OAuth2ClientContext context) {
    return new OAuth2RestTemplate(details, context);
  }

}
