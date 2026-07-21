package io.github.jerezarza98.bazarapi.exception;

public class ProductoNoEncontradoException extends ResourceNotFoundException {
    public ProductoNoEncontradoException() {
        super("El producto no existe");
    }
}
