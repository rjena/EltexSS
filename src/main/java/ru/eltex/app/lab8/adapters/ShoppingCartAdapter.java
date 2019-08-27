package ru.eltex.app.lab8.adapters;

import com.google.gson.*;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab3.ShoppingCart;

import java.lang.reflect.Type;
import java.util.List;

public class ShoppingCartAdapter implements JsonSerializer<ShoppingCart> {
    @Override
    public JsonElement serialize(ShoppingCart src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        JsonArray cartItems = new JsonArray();
        List cartList = src.getCartItems();
        for (Object e : cartList)
            cartItems.add(context.serialize(e, Electronic.class));
        jsonObject.add("shoppingCart", cartItems);
        return jsonObject;
    }
}