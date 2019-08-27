package ru.eltex.app.lab8.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.ShoppingCart;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class OrderAdapter implements JsonSerializer<Order> {
    @Override
    public JsonElement serialize(Order src, Type typeOfSrc, JsonSerializationContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId().toString());
        jsonObject.addProperty("status", src.getStatus());
        jsonObject.addProperty("creationTime", sdf.format(src.getCreationTime()));
        jsonObject.addProperty("waitingTime", src.getWaitingTime());
        jsonObject.add("credentials", context.serialize(src.getCredentials(), Credentials.class));
        jsonObject.add("cart", context.serialize(src.getCart(), ShoppingCart.class));
        if (src.getIp() != null) jsonObject.addProperty("client_ip", src.getIp());
        return jsonObject;
    }
}
