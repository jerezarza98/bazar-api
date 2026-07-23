package io.github.jerezarza98.bazarapi.exception;

public class ProductoSinStockException extends ConflictException {
    public ProductoSinStockException() {
        super("El stock del producto no puede ser negativo");
    }
}
