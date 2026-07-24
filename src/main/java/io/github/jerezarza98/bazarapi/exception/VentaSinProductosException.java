package io.github.jerezarza98.bazarapi.exception;

public class VentaSinProductosException extends BadRequestException {
    public VentaSinProductosException() {
        super("La venta no tiene productos");
    }
}
