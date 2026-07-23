package io.github.jerezarza98.bazarapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaVenta;
    private Double total;

    @ManyToMany
    private List<Producto> productos;

    @ManyToOne
    private Cliente cliente;

    public Venta(LocalDate fechaVenta, List<Producto> productos, Cliente cliente) {
        this.fechaVenta = fechaVenta;
        this.productos = productos;
        this.cliente = cliente;
        this.total = productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

}
