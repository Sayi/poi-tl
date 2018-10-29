package com.deepoove.poi.el;

public class ExpressionEvalException extends RuntimeException {

    private static final long serialVersionUID = -2950830923479581904L;

    public ExpressionEvalException(String msg) {
        super(msg);
    }

    public ExpressionEvalException(String msg, Throwable e) {
        super(msg, e);
    }

}
