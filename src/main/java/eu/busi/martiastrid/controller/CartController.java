package eu.busi.martiastrid.controller;

import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.CartService;
import eu.busi.martiastrid.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static eu.busi.martiastrid.constants.Constants.CART;
import static eu.busi.martiastrid.constants.Constants.PIZZA_TO_EDIT;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_MESSAGE;

@Controller
@RequestMapping("/cart")
@SessionAttributes(CART)
public class CartController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute(CART)
    public HashMap<Pizza, Integer> setupCart() {
        return new HashMap<>();
    }

    @GetMapping
    public String showCartPage(Model model,
                               Principal principal,
                               Locale locale) {
        model.addAttribute(PIZZA_TO_EDIT, new Pizza());
        boolean isUserConnected = !Objects.isNull(principal);
        if (isUserConnected) {
            try {
                orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                model.addAttribute(CART, cartService.loadCartFromDatabase());
            } catch (PizzaException e) {
                model.addAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
            }
        }
        //else the session attribute cart will be used

        return "integratedWithMenu:cart";
    }

    @PostMapping("/add")
    public String addId(Model model,
                        HttpServletRequest request,
                        @ModelAttribute(PIZZA_TO_EDIT) Pizza pizza,
                        @SessionAttribute(CART) HashMap<Pizza, Integer> cart,
                        Locale locale) {

        boolean isUserConnected = !Objects.isNull(request.getUserPrincipal());
        if (!isUserConnected) {
            // not connected : only remove from cart
            cartService.incrementPizzaInCart(pizza, cart);
        } else {
            try {
                Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                order.incrementPizza(pizza.getId());
                orderService.saveOrderInDatabase(order);
                model.addAttribute(CART, cartService.loadCartFromDatabase());
            } catch (PizzaException e) {
                model.addAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeId(Model model,
                           HttpServletRequest request,
                           @ModelAttribute(PIZZA_TO_EDIT) Pizza pizza,
                           @SessionAttribute(CART) HashMap<Pizza, Integer> cart,
                           Locale locale) {
        Principal principal = request.getUserPrincipal();
        if (Objects.isNull(principal)) {
            // not connected : only remove from cart
            cartService.decrementPizzaInCart(pizza, cart);
        } else {
            try {
                Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                order.decrementPizza(pizza.getId());
                orderService.saveOrderInDatabase(order);
                model.addAttribute(CART, cartService.loadCartFromDatabase());
            } catch (PizzaException e) {
                model.addAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
            }
        }
        return "redirect:/cart";
    }

}
