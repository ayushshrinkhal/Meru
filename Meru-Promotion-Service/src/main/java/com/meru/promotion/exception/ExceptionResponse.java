package com.meru.promotion.exception;

public class ExceptionResponse {
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public String getRequestedUrl() {
        return requestedUrl;
    }

   public void callerURL(final String requestedUrl){
        this.requestedUrl=requestedUrl;
   }

    private String requestedUrl;

}
