package ru.eltex.app.lab6;

import ru.eltex.app.lab1.Electronic;
import ru.eltex.app.lab2.Credentials;
import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.ShoppingCart;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Arrays;
import java.util.Date;

public class Client implements Runnable {
    private final int PORTLISTEN = 8087;
    private static int count = 0;
    private Credentials credentials;
    private String clientNo;
    private boolean statusNotification;
    private Order order;

    public Client() {
        credentials = new Credentials("Surname" + ++count, "Name" + count,
                "Patronym" + count, "email" + count + "@gmail.com");
        clientNo = "Client" + count;
        new Thread(this, clientNo).start();
        System.out.println(clientNo + " loaded\n");
        statusNotification = true;
    }

    @Override
    public void run() {
        while (true) {
            if (statusNotification) {
                /** Клиент слушает UDP-порт и ждет оповещения */
                try (DatagramSocket ds = new DatagramSocket(PORTLISTEN)) {
                    DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                    ds.receive(dp);
                    /** Получив оповещение, клиент извлекает порт и IP сервера из пакета
                     *  и устанавливает соединение по TCP. Оправляет заказ. Отсоединяется от сервера */
                    int port = Integer.parseInt(new String(Arrays.copyOf(dp.getData(), dp.getLength())));
                    InetAddress ip = dp.getAddress();
                    order = new Order(credentials, new ShoppingCart<Electronic>(), ip);
                    try (ObjectOutputStream oos = new ObjectOutputStream(new Socket(ip, port).getOutputStream())) {
                        oos.writeObject(order);
                        statusNotification = false;
                    }
                } catch (IOException e) { e.getMessage(); }
            } else {
                /** Ждет оповещения о статусе заказа по UDP и генерирует новые заказы если оповещение получено */
                try (DatagramSocket dsn = new DatagramSocket(PORTLISTEN)) {
                    DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                    dsn.receive(dp);
                    if (new String(Arrays.copyOf(dp.getData(), dp.getLength())).equals("Processed")) {
                        statusNotification = true;
                        /** Клиент, получив оповещение о изменении статуса заказа,
                         *  выводит на консоль время обработки заказа и информацию о заказе. */
                        System.out.println(clientNo + "'s order was processed at " + new Date());
                        System.out.println(order.toString());
                    }
                } catch (IOException e) { e.getMessage(); }
            }
        }
    }

    public static void main(String[] args) {
        new Client();
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.getMessage(); }
        new Client();
    }
}
