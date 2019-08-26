package ru.eltex.app.lab7;

public class DeletingException extends RuntimeException {
    public DeletingException(int er) {
        super(new String[]{"Нет такого заказа", "Файл поврежден", "Неправильная команда"}[er - 1]);
    }
}
