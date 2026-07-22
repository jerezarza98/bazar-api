package io.github.jerezarza98.bazarapi.exception;

public class ClienteNoEncontradoException extends ResourceNotFoundException {
    public ClienteNoEncontradoException() {
        super("El cliente no existe");
    }
}
