FROM openjdk:17

ADD target/account-service-ui.jar /usr/local/account-service-ui/

WORKDIR /usr/local/account-service-ui/

CMD ["java", "-jar", "account-service-ui.jar"]