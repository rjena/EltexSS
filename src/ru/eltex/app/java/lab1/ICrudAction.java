package ru.eltex.app.java.lab1;

public interface ICrudAction {
    // заполнение объекта случайными значениями и инкремент счётчика.
    void create();

    // вывод данных на экран.
    void read();

    // ввод данных с клавиатуры.
    void update();

    // принудительное зануление данных в объекте и декремент счетчика.
    void delete();
}