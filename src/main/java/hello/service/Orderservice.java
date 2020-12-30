package hello.service;

import javax.inject.Inject;

public class Orderservice {
    private UserService userService;

    @Inject
    public Orderservice(UserService userService) {
        this.userService = userService;
    }

    //public void placeOrder(Integer userId, String item) {
    //    userService.getUserById(userId);
    //}
}
