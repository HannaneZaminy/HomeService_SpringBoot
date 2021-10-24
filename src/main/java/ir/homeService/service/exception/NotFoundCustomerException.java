package ir.homeService.service.exception;

public class NotFoundCustomerException extends Exception {
    public NotFoundCustomerException(String customer_is_not_available) {
        super(customer_is_not_available);
    }
}
