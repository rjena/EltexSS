package ru.eltex.app.lab8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eltex.app.lab3.ShoppingCart;

@Repository
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Integer> {
}
