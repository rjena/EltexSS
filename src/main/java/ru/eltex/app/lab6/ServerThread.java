package ru.eltex.app.lab6;

import ru.eltex.app.lab3.Order;
import ru.eltex.app.lab3.Orders;
import ru.eltex.app.lab4.CheckPending;
import ru.eltex.app.lab4.CheckProcessed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ServerThread implements Runnable {
    private static final String BROADCAST = "192.168.53.255"; //"172.21.0.255";
    private static final int PORT = 8080;
    private static final int PORTNOTIFY = 8087;
    private static Orders<Order> orders;
    private Socket socket;

    public static void main(String[] args) {
        orders = new Orders<>();

        /** Организовать на сервере автоматическую обработку заказов */
        CheckPending pendingChecker = new CheckPending(orders);
        try { Thread.sleep(2500); } catch (InterruptedException e) { e.getMessage(); }
        CheckProcessed processedChecker = new CheckProcessed(orders);

        Notification notifications = new Notification(BROADCAST, PORT, PORTNOTIFY);

        try (ServerSocket ss = new ServerSocket(PORT)) {
            while (true) new ServerThread(ss.accept());
        } catch (IOException e) { e.getMessage(); }
        notifications.setStop();
        pendingChecker.setStop();
        processedChecker.setStop();
    }

    private ServerThread(Socket socket) {
        System.out.println("Client connection");
        this.socket = socket;
        new Thread(this, "Client " + socket.getInetAddress() + ":" + socket.getPort()).start();
    }

    @Override
    public void run() {
        /** Сервер, получив заказ, складывает его в очередь заказов */
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Order o = (Order) ois.readObject();
            orders.offer(o.getCart(), o.getCredentials(), o.getIp());
        } catch (IOException | ClassNotFoundException ex) { ex.getMessage(); }
    }
}

/** Сервер по UDP делает оповещение в локальную сеть, высылая внутри пакета порт на соединение */
class Notification implements Runnable {
    private String BROADCAST;
    private int PORT;
    private int PORTNOTIFY;
    private static boolean stop;
    private Thread thread;

    Notification(String broadcast, int port, int notify) {
        this.BROADCAST = broadcast;
        this.PORT = port;
        this.PORTNOTIFY = notify;
        stop = false;
        thread = new Thread(this, "Notifications");
        System.out.println("New thread: " + thread);
        thread.start();
    }

    @Override
    public void run() {
        while (!stop) {
            try (DatagramSocket socket = new DatagramSocket()) {
                byte[] buf = String.valueOf(PORT).getBytes();
                socket.send(new DatagramPacket(buf, buf.length, InetAddress.getByName(BROADCAST), PORTNOTIFY));
            } catch (IOException e) { e.getMessage(); }
            try { Thread.sleep(500); } catch (InterruptedException e) { e.getMessage(); }
        }
    }

    void setStop() { stop = true; }
}