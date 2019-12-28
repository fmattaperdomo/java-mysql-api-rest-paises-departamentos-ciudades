package com.fmattaperdomo.restful.exceptions;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class GeneralException extends RuntimeException {
    public GeneralException() {
        this("Excepcion General no especificada");
    }
    
    public GeneralException(String msg) {
        super(msg);
    }
}
