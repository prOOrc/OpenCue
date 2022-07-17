
/*
 * Copyright Contributors to the OpenCue Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.imageworks.spcue.config;

import com.imageworks.spcue.servlet.JobLaunchServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@ImportResource({"classpath:conf/spring/applicationContext-dbEngine.xml",
                 "classpath:conf/spring/applicationContext-grpc.xml",
                 "classpath:conf/spring/applicationContext-grpcServer.xml",
                 "classpath:conf/spring/applicationContext-service.xml",
                 "classpath:conf/spring/applicationContext-jms.xml",
                 "classpath:conf/spring/applicationContext-rabbit.xml",
                 "classpath:conf/spring/applicationContext-kafka.xml",
                 "classpath:conf/spring/applicationContext-criteria.xml"})
@EnableConfigurationProperties
@PropertySource({"classpath:opencue.properties"})
public class AppConfig {

    @Configuration
    @Conditional(PostgresDatabaseCondition.class)
    @ImportResource({"classpath:conf/spring/applicationContext-dao-postgres.xml"})
    static class PostgresEngineConfig {}

    @Bean
    @Primary
    @ConfigurationProperties(prefix="datasource.cue-data-source")
    public DataSource cueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Value("${kafka.brokers}")
    private String bootstrapServers;

    @Value("${kafka.user}")
    private String kafkaUser;

    @Value("${kafka.password}")
    private String kafkaPassord;

    @Value("${truststore.location}")
    private String trustStoreLocation;

    @Value("${truststore.password}")
    private String trustStorePassword;

    @Bean
    public Map<String, Object> producerConfigs() {
        
        String serializer = StringSerializer.class.getName();
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);

        if (!kafkaUser.equals("")) {
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", "SCRAM-SHA-512");
            String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
            String jaasCfg = String.format(jaasTemplate, kafkaUser, kafkaPassord);
            props.put("sasl.jaas.config", jaasCfg);
            props.put("ssl.truststore.location", trustStoreLocation);
            props.put("ssl.truststore.password", trustStorePassword);
        }

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic jobLaunched() {
        return TopicBuilder.name("job.launched").build();
    }

    @Bean
    public NewTopic jobFinished() {
        return TopicBuilder.name("job.finished").build();
    }

    @Bean
    public NewTopic frameStarted() {
        return TopicBuilder.name("frame.started").build();
    }

    @Bean
    public NewTopic frameCompleted() {
        return TopicBuilder.name("frame.completed").build();
    }

    @Bean
    public NewTopic hostReported() {
        return TopicBuilder.name("host.reported").build();
    }

    @Bean
    public ServletRegistrationBean<JobLaunchServlet> jobLaunchServlet() {
        ServletRegistrationBean<JobLaunchServlet> b = new ServletRegistrationBean<>();
        b.addUrlMappings("/launch");
        b.addInitParameter("contextConfigLocation", "classpath:conf/spring/jobLaunchServlet-servlet.xml");
        b.setServlet(new JobLaunchServlet());
        return b;
    }
}

