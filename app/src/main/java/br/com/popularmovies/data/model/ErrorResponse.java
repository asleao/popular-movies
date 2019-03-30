package br.com.popularmovies.data.model;

public class ErrorResponse {
    private final int statusCode;
    private final String statusMessage;

    public ErrorResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
