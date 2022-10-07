package com.ssafy.dockchodogam.kafka;

import com.ssafy.dockchodogam.dto.battle.BattleLogDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class BattleLogProducerConfig {
    @Value("localhost:9092")

    private String bootstrapServer;

    @Bean
    public Map<String, Object> battleLogRecordProducerConfigs() {
        return CommonJsonSerializer.getStringObjectMap(bootstrapServer);
    }

    @Bean
    public ProducerFactory<String, BattleLogDto> battleLogDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(battleLogRecordProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BattleLogDto> battleLogDtoKafkaTemplate() {
        return new KafkaTemplate<>(battleLogDtoProducerFactory());
    }
}
