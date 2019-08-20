package ru.eltex.app.java.lab7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.eltex.app.java.lab3.Order;
import ru.eltex.app.java.lab3.Orders;

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
        Orders<Order> orders = new ManagerJSON().readAll();
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
        Order order = new ManagerJSON().readById(id);
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
        logger.info("ACT: Adding new Electronic to Shopping Cart with ID: " + id);
        String oid = new ManagerJSON().addToCardAndGetOrderId(id);
        if (oid.equals("incorrect")) {
            logger.info("RES: Incorrect Shopping Cart ID: " + id);
            return "Incorrect ID!!! [ should be Integer more than 0 ]";
        } else {
            logger.info("RES: New Electronic with ID: " + oid + " was added to Shopping Cart with ID: " + id);
            return "New Electronic ID: " + oid.toString();
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
        String res = new ManagerJSON().delById(id);
        if (!res.equals("0")) throw new DeletingException(Integer.parseInt(res));
        else logger.info("RES: Order with ID: " + id + " was deleted");
        return res;
    }

    @ExceptionHandler(DeletingException.class)
    public String handleException(DeletingException  e) {
        return ">1";
    }
}