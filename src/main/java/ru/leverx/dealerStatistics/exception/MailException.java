package ru.leverx.dealerStatistics.exception;

public class MailException extends RuntimeException {
    private static final long serialVersionUID = 1515502198795323189L;

    public MailException(String message) {
        super(message);
    }
}
