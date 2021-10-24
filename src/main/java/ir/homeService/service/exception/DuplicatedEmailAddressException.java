package ir.homeService.service.exception;

public class DuplicatedEmailAddressException extends Exception {
    public DuplicatedEmailAddressException(String s) {
        super(s);
    }
}
