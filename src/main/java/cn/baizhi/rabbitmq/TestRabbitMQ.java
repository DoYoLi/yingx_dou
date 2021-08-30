package cn.baizhi.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("helloworld"))
public class TestRabbitMQ {

//    private RabbitTemplate rabbitTemplate
    @RabbitHandler
    public void consumer(String massage){
        System.out.println("消费者:"+massage);
    }
}
