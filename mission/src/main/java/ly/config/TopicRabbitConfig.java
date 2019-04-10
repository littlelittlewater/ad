package ly.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    public final static String MISSION_ALL = "mission_all";

    public final static String PUBLISH_ERROR= "publish_error";
    public final static String PUBLISH_SUCCESS = "publish_success";

    public final static String MISSION_PUBLISH_EXCHANGE = "missionExchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MISSION_PUBLISH_EXCHANGE);
    }

    @Bean
    public Queue queuePublishError() {
        return new Queue(PUBLISH_ERROR);
    }

    @Bean
    Binding bindingExchangeMessageA(Queue queuePublishError, TopicExchange exchange) {
        return BindingBuilder.bind(queuePublishError).to(exchange).with("*.error");
    }

    @Bean
    public Queue queuePublishSuccess() {
        return new Queue(PUBLISH_SUCCESS);
    }

    @Bean
    Binding bindingExchangeMessageB(Queue queuePublishSuccess, TopicExchange exchange) {
        return BindingBuilder.bind(queuePublishSuccess).to(exchange).with("*.success");
    }

    @Bean
    public Queue queueMissionAll() {
        return new Queue(MISSION_ALL);
    }

    @Bean
    Binding bindingExchangeMessageC(Queue queueMissionAll, TopicExchange exchange) {
        return BindingBuilder.bind(queueMissionAll).to(exchange).with("#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}