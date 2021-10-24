package ir.homeService.service;

public interface SecurityService {

    void autoLogin(String username, String password);

    String findLoggedInUsername();
}
