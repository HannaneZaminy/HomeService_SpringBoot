package ir.homeService.service.exception;

public class NotFoundOrderForExpertException extends Exception{
    public NotFoundOrderForExpertException(String fa_ir) {
        super(fa_ir);
    }
}
