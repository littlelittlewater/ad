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
    public final static String BASIC_PUBLISH = "basic.publish";

    public final static String MISSION_PUBLISH_EXCHANGE = "missionExchange";

    @Bean
    public Queue basicPublic() {
        return new Queue(BASIC_PUBLISH);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MISSION_PUBLISH_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessageC(Queue basicPublic, TopicExchange exchange) {
        return BindingBuilder.bind(basicPublic).to(exchange).with("basic.publish");
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