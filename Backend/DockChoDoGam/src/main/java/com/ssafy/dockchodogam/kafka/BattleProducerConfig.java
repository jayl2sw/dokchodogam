package com.ssafy.dockchodogam.kafka;

import com.ssafy.dockchodogam.dto.battle.BattleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class BattleProducerConfig {

    @Value("localhost:9092")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> battleRecordProducerConfigs() {
        return CommonJsonSerializer.getStringObjectMap(bootstrapServer);
    }

    @Bean
    public ProducerFactory<String, BattleDto> battleDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(battleRecordProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BattleDto> battleDtoKafkaTemplate() {
        return new KafkaTemplate<>(battleDtoProducerFactory());
    }
}
