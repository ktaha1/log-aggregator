package com.tahakamil.kafka.logaggregator.publisher.config.properties;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class KafkaPropertiesLoader {

    public static KafkaProperties load(String yamlFile) {
        Constructor constructor = new Constructor(KafkaWrapper.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);

        InputStream inputStream = KafkaPropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(yamlFile);

        KafkaWrapper wrapper = yaml.load(inputStream);
        return wrapper.getKafka();
    }

    public static KafkaProperties loadDefault() {
        return load("properties.yaml");
    }
}
