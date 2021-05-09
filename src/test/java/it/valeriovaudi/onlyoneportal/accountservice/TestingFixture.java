package it.valeriovaudi.onlyoneportal.accountservice;


import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Date;
import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Phone;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.Locale;
import java.util.Map;

public class TestingFixture {


    public static Account anAccount() {
        return new Account("FIRST_NAME",
                "LAST_NAME",
                Date.dateFor("01/01/1970"),
                "user.mail@mail.com",
                Phone.nullValue(),
                Locale.ENGLISH
        );
    }

    public static String anAccountAsJsonString() {
        return "{\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"birthDate\":\"01/01/1970\",\"mail\":\"user.mail@mail.com\",\"phone\":\"\"}";
    }

    public static Map<String, String> i18nsMessage() {
        return Map.of("key1", "value1");
    }

    public static String ACCOUNT_MAIL = "user.mail@mail.com";

    public static ReactiveRedisTemplate newReactiveRedisTemplate() {
        RedisSerializationContext<Object, Object> serializationContextBuilder = RedisSerializationContext.java();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost", 36379);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
        connectionFactory.afterPropertiesSet();

        return new ReactiveRedisTemplate(connectionFactory, serializationContextBuilder);
    }

}