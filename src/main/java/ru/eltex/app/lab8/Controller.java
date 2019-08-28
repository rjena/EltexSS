package ru.eltex.app.lab8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;
import ru.eltex.app.lab3.ShoppingCart;
import ru.eltex.app.lab7.DeletingException;
import ru.eltex.app.lab8.adapters.CredentialsAdapter;
import ru.eltex.app.lab8.adapters.ElectronicAdapter;
import ru.eltex.app.lab8.adapters.OrderAdapter;
import ru.eltex.app.lab8.adapters.ShoppingCartAdapter;

import java.util.HashMap;

@RestController
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    @Autowired private ServiceDB service;
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Electronic.class, new ElectronicAdapter())
            .registerTypeAdapter(ShoppingCart.class, new ShoppingCartAdapter())
            .registerTypeAdapter(Credentials.class, new CredentialsAdapter())
            .registerTypeAdapter(Order.class, new OrderAdapter())
            .setPrettyPrinting()
            .create();
    private final Gson gsonForPrettyInBrowser = new GsonBuilder()
            .registerTypeAdapter(Electronic.class, new ElectronicAdapter()).create();

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=readall
     * возвращаются все заказы в виде JSON.
     * */
    @GetMapping(value = "/", params = {"command=readall"})
    public Orders readAll() {
        logger.info("ACT: Reading all Orders");
        Orders<Order> orders = service.readAll();
        if (orders != null) logger.info("RES: All Orders were read");
        else logger.info("RES: There are no one Order...");
        return gsonForPrettyInBrowser.fromJson(gson.toJson(orders), Orders.class);
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=readById&order_id=[id]
     * возвращается обратно заказ с идентификатором [id] в виде JSON.
     * */
    @GetMapping(value = "/", params = {"command=readById", "order_id"})
    public Order readById(@RequestParam("order_id") String id) {
        logger.info("ACT: Reading Order by ID: " + id);
        Order order = service.readById(id);
        if (order != null) {
            logger.info("RES: Order with ID: " + id + " was read");
            return gsonForPrettyInBrowser.fromJson(gson.toJson(order), Order.class);
        } else {
            logger.info("RES: There are no Order with ID: " + id);
            return null;
        }
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=addToCard&card_id=[id]
     * генерируется товар и добавляется в корзину с идентификатором [id].
     * Пользователю возвращается id нового товара.
     * */
    @RequestMapping(value = "/", params = {"command=addToCard", "card_id"})
    public String addToCard(@RequestParam("card_id") String id) {
        logger.info("ACT: Adding new Electronic to Shopping Cart with ID: " + id);
        String oid = service.addToCardAndGetOrderId(id);
        if (oid.equals("incorrect")) {
            logger.info("RES: Incorrect Shopping Cart ID: " + id);
            return "Incorrect ID!!! [ should be Integer more than 0 ]";
        } else {
            logger.info("RES: New Electronic with ID: " + oid + " was added to Shopping Cart with ID: " + id);
            return "New Electronic ID: " + oid;
        }
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=delById&order_id=[id]
     * удаляется заказ с идентификатором [id].
     * Пользователю возвращается 0 в случае успешного удаления и >1 если была ошибка.
     * */
    @GetMapping(value = "/", params = {"command=delById", "order_id"})
    public String delById(@RequestParam("order_id") String id) {
        logger.info("ACT: Deleting Order by ID: " + id);
        String res = service.delById(id);
        if (!res.equals("0")) throw new DeletingException(Integer.parseInt(res));
        else logger.info("RES: Order with ID: " + id + " was deleted");
        return res;
    }

    @GetMapping(value = "/", params = {"command=start"})
    public String start() {
        service.addOrders();
        return "OK";
    }

    @ExceptionHandler(DeletingException.class)
    public String handleException(DeletingException e) {
        HashMap<String, Integer> out = new HashMap<>();
        out.put("Нет такого заказа", 1);
        out.put("Неправильная команда", 3);
        return out.get(e.getMessage()).toString();
    }
}
