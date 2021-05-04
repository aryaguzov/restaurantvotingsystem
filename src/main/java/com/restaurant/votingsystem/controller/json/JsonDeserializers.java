package com.restaurant.votingsystem.controller.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.model.User;

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


}
