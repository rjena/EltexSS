package ru.eltex.app.lab8.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.eltex.app.lab2.Credentials;

import java.lang.reflect.Type;

public class CredentialsAdapter implements JsonSerializer<Credentials> {
    @Override
    public JsonElement serialize(Credentials src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        String className = src.getClass().getName();
        jsonObject.addProperty("id", src.getId().toString());
        jsonObject.addProperty("surname", src.getSurname());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("patronym", src.getPatronym());
        jsonObject.addProperty("email", src.getEmail());
        return jsonObject;
    }
}
