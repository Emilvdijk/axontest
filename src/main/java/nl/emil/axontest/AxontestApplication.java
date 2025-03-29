package nl.emil.axontest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AxontestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxontestApplication.class, args);
	}
	@Qualifier("messageSerializer")
	@Bean
	public Serializer messageSerializer(ObjectMapper mapper) {
		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());

		return JacksonSerializer.builder()
				.objectMapper(mapper)
				.lenientDeserialization()
				.build();
	}
	//https://github.com/AxonFramework/AxonFramework/issues/1418#issuecomment-1328792446

}
