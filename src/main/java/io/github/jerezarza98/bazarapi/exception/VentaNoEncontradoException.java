package io.github.jerezarza98.bazarapi.exception;

public class VentaNoEncontradoException extends ResourceNotFoundException {
    public VentaNoEncontradoException() {
        super("La venta no existe");
    }
}
