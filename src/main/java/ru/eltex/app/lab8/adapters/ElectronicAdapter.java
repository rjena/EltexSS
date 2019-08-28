package ru.eltex.app.lab8.adapters;

import com.google.gson.*;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab1.Phone;
import ru.eltex.app.lab1.Smartphone;
import ru.eltex.app.lab1.Tablet;

import java.lang.reflect.Type;

public class ElectronicAdapter implements JsonSerializer<Electronic>, JsonDeserializer<Electronic> {
    @Override
    public JsonElement serialize(Electronic src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("electronicType", src.getClass().getName());
        if (src.getClass().getSimpleName().equals("Phone")) {
            jsonObject.addProperty("caseType", ((Phone) src).getCaseType());
        } else if (src.getClass().getSimpleName().equals("Smartphone")) {
            jsonObject.addProperty("simType", ((Smartphone) src).getSimType());
            jsonObject.addProperty("simCount", ((Smartphone) src).getSimCount());
        } else if (src.getClass().getSimpleName().equals("Tablet")) {
            jsonObject.addProperty("videoProcessor", ((Tablet) src).getVideoProcessor());
            jsonObject.addProperty("screenResolution", ((Tablet) src).getScreenResolution());
        }
        jsonObject.addProperty("id", src.getID().toString());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("price", src.getPrice());
        jsonObject.addProperty("firm", src.getFirm());
        jsonObject.addProperty("model", src.getModel());
        jsonObject.addProperty("os", src.getOS());
        return jsonObject;
    }

    @Override
    public Electronic deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Class<?> klass = null;
        try { klass = Class.forName(jsonObject.get("electronicType").getAsString()); }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject, klass);
    }
}
