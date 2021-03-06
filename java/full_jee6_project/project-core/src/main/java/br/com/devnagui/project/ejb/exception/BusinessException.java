package br.com.devnagui.project.ejb.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<String> parametros = new ArrayList<String>();

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(String message) {
        super(message);
        if (message == null) {
            throw new IllegalArgumentException("message == null");
        }
    }

    public BusinessException(String... parametros) {
        this.parametros = Arrays.asList(parametros);
    }

    public BusinessException(List<String> parametros) {
        this.parametros = parametros;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        if (message == null || cause == null) {
            throw new IllegalArgumentException("message == null || cause == null");
        }
    }

    public List<String> getMessagesWithParam() {
        return parametros;
    }

    public boolean isMessageParametrized() {
        return !parametros.isEmpty();
    }
}
