package ru.eltex.app.java.lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.eltex.app.java.lab1.Electronic;
import ru.eltex.app.java.lab1.Phone;
import ru.eltex.app.java.lab1.Smartphone;
import ru.eltex.app.java.lab1.Tablet;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;
import ru.eltex.app.java.lab3.ShoppingCart;
import ru.eltex.app.java.lab5.ElectronicAdapter;
import ru.eltex.app.java.lab5.ManagerOrderJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@RestController
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=readall
     * возвращаются все заказы в виде JSON.
     * */
    @GetMapping(value = "/", params = {"command=readall"})
    public Orders<Order> readAll() {
        logger.info("ACT: Reading all Orders");
        Orders<Order> orders = new ManagerOrderJSON().readAll();
        if (orders != null) logger.info("RES: All Orders were read");
        else logger.info("RES: There are no one Order...");
        return orders;
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=readById&order_id=[id]
     * возвращается обратно заказ с идентификатором [id] в виде JSON.
     * */
    @GetMapping(value = "/", params = {"command=readById", "order_id"})
    public Order readById(@RequestParam("order_id") String id) {
        logger.info("ACT: Reading Order by ID: " + id);
        Order order = new ManagerOrderJSON().readById(UUID.fromString(id));
        if (order != null) logger.info("RES: Order with ID: " + id + " was read");
        else logger.info("RES: There are no Order with ID: " + id);
        return order;
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=addToCard&card_id=[id]
     * генерируется товар и добавляется в корзину с идентификатором [id].
     * Пользователю возвращается id нового товара.
     * */
    @RequestMapping(value = "/", params = {"command=addToCard", "card_id"})
    public String addToCard(@RequestParam("card_id") String id) {
        logger.info("ACT: Adding new Order to Shopping Card with ID: " + id);
        UUID oid = addToCardAndGetOrderId(Integer.parseInt(id));
        if (oid != null) {
            logger.info("RES: New Electronic with ID: " + oid.toString() +
                    " was added to Shopping Card with ID: " + id);
            return "New Order ID: " + oid.toString();
        }
        else logger.info("RES: Cannot find Shopping Card with ID: " + id);
        return null;
    }

    /**
     * При запросе в строке браузера http://localhost:[port]/?command=delById&order_id=[id]
     * удаляется заказ с идентификатором [id].
     * Пользователю возвращается 0 в случае успешного удаления и >1 если была ошибка.
     * */
    @GetMapping(value = "/", params = {"command=delById", "order_id"})
    public String delById(@RequestParam("order_id") String id) {
        logger.info("ACT: Deleting Order by ID: " + id);
        String res = new ManagerOrderJSON().delById(id);
        String[] logs = {"Order with ID: " + id + " was deleted",
                "Cannot find Order with ID: " + id, "File is corrupted", "Wrong command"};
        logger.info("RES: " + logs[Integer.parseInt(res)]);
        return res;
    }

    private UUID addToCardAndGetOrderId(int id) {
        String pathname = new File("").getAbsolutePath() + "/src/ru/eltex/app/java/lab7/files/carts.txt";
        File file = new File(pathname);
        ArrayList<ShoppingCart<Electronic>> carts = new ArrayList<>();
        ShoppingCart<Electronic> cart = null;
        if (file.exists()) {
            if (file.length() != 0)
                try (JsonReader jr = new JsonReader(new FileReader(file))) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Electronic.class, new ElectronicAdapter()).create();
                    jr.setLenient(true);
                    ShoppingCart<Electronic> sc;
                    while (jr.peek() != JsonToken.END_DOCUMENT)
                        try {
                            sc = gson.fromJson(jr, ShoppingCart.class);
                            if (id != sc.getId()) carts.add(sc); else cart = sc;
                        } catch (JsonIOException | JsonSyntaxException e) { e.getMessage(); }
                } catch (IOException e) { e.getMessage(); }
        } else try { file.createNewFile(); } catch (IOException e) { e.getMessage(); }
        if (cart != null)
            try (FileWriter fw = new FileWriter(file)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Electronic.class, new ElectronicAdapter()).setPrettyPrinting().create();
                for (ShoppingCart<Electronic> sc : carts) fw.write(gson.toJson(sc) + "\n");
                Electronic e;
                switch (new Random().nextInt(3)) {
                    case 0: e = new Phone();
                    case 1: e  = new Smartphone();
                    default: e = new Tablet();
                }
                e.create();
                cart.add(e);
                fw.write(gson.toJson(cart) + "\n");
                return e.getID();
            } catch (IOException e) { e.getMessage(); }
        return null;
    }
}
