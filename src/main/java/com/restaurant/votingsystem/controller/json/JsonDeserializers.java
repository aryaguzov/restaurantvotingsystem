package com.restaurant.votingsystem.controller.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

public class JsonDeserializers {
    public static class RestaurantJsonDeserializer extends JsonDeserializer<Restaurant> {

        @Override
        public Restaurant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser == null) {
                return null;
            }
            Restaurant restaurant = new Restaurant();
            restaurant.setId(Integer.valueOf(jsonParser.getText()));
            return restaurant;
        }
    }

    public static class UserJsonDeserializer extends JsonDeserializer<User> {

        @Override
        public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser == null) {
                return null;
            }
            User user = new User();
            user.setId(Integer.valueOf(jsonParser.getText()));
            return user;
        }
    }

    // https://stackoverflow.com/a/30723658/15020481
    public static class BCryptPasswordDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(node.asText());
        }
    }


}
