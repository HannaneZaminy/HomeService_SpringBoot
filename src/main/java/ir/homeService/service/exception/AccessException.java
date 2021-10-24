package ir.homeService.service.exception;

public class AccessException extends Exception {
    public AccessException(String fa_ir) {
        super(fa_ir);
    }
}
