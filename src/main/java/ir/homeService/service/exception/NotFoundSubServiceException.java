package ir.homeService.service.exception;

public class NotFoundSubServiceException extends Exception {
    public NotFoundSubServiceException(String sub_service_not_found) {
        super(sub_service_not_found);
    }
}
