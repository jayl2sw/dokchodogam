package com.ssafy.dockchodogam.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class PlantIdProducerConfig {
    @Value("localhost:9092")

    private String bootstrapServer;

    @Bean
    public Map<String, Object> plantRecordProducerConfigs() {
        return CommonJsonSerializer.getStringObjectMap(bootstrapServer);
    }

    @Bean
    public ProducerFactory<String, String> plantDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(plantRecordProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> plantDtoKafkaTemplate() {
        return new KafkaTemplate<>(plantDtoProducerFactory());
    }
}
