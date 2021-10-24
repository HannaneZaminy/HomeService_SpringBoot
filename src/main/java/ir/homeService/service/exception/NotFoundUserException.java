package ir.homeService.service.exception;

public class NotFoundUserException extends Exception {
    public NotFoundUserException(String fa_ir) {
        super(fa_ir);
    }
}
