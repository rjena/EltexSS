package ru.eltex.app.java.lab5;

import com.google.gson.*;
import ru.eltex.app.java.lab1.Electronic;

import java.lang.reflect.Type;

public class ElectronicAdapter implements JsonSerializer<Electronic>, JsonDeserializer<Electronic> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public JsonElement serialize(Electronic src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = context.serialize(src);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public Electronic deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);
    }
}