package io.github.jerezarza98.bazarapi.model;

import io.github.jerezarza98.bazarapi.exception.ProductoSinStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String marca;
    private Double precio;
    private int stock;

    public Producto(String nombre, String marca, Double precio, int stock) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
    }

    public void descontarStock() {
        if(this.stock == 0) {
            throw new ProductoSinStockException();
        }
        this.stock--;
    }

    public void aumentarStock() {
        this.stock++;
    }
}
