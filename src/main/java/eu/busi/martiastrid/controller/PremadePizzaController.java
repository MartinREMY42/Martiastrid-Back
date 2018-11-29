package eu.busi.martiastrid.controller;

import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.form.PizzaCounter;
import eu.busi.martiastrid.service.CartService;
import eu.busi.martiastrid.service.IngredientService;
import eu.busi.martiastrid.service.OrderService;
import eu.busi.martiastrid.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static eu.busi.martiastrid.constants.Constants.CART;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_MESSAGE;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_PIZZA_AMOUNT;

@Controller
@RequestMapping("/standardPizzas")
@SessionAttributes(CART)
public class PremadePizzaController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;


    @ModelAttribute(CART)
    public HashMap<Pizza, Integer> setupCart() {
        return new HashMap<>();
    }

    @GetMapping
    public String showStandardPizzas(Model model) {
        model.addAttribute("allPizzas", pizzaService.getAllStandard());
        model.addAttribute("pizzaCounter", new PizzaCounter());
        return "integratedWithMenu:premadePizzas";
    }



    @GetMapping(params = "category")
    public String showStandardPizzasFilter(Model model,
                                           @RequestParam("category") String category,
                                           Locale locale) {
        try {
            Collection<Pizza> allPizzas = pizzaService.getAllWithCategory(category);
            model.addAttribute("allPizzas", allPizzas);
            model.addAttribute("pizzaCounter", new PizzaCounter());
        } catch (PizzaException e) {
            model.addAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
        }
        return "integratedWithMenu:premadePizzas";
    }

    @PostMapping("/add")
    public String addToCartStandardPizzas(HttpServletRequest request,
                                          Model model,
                                          @ModelAttribute("pizzaCounter") PizzaCounter pizzaCounter,
                                          @SessionAttribute(CART) HashMap<Pizza, Integer> cart,
                                          Locale locale,
                                          final RedirectAttributes redirectAttributes) {

        boolean isUserConnected = !Objects.isNull(request.getUserPrincipal());

        try {
            if (isUserConnected) {
                Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                orderService.addAllPizzasToOrder(pizzaCounter.getOrderedPizzas(), order);
                orderService.saveOrderInDatabase(order);
                model.addAttribute(CART, cartService.loadCartFromDatabase());
            } else {
                // HashMap<Pizza, Integer> orderedPizzas = new HashMap<>();
                if (pizzaCounter.getOrderedPizzas().values().stream().noneMatch(integer -> !Objects.isNull(integer) && integer > 0)) {
                    redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(ERROR_PIZZA_AMOUNT, null, locale));
                    return "redirect:/standardPizzas";
                }
                if (pizzaCounter.getOrderedPizzas().keySet().stream().noneMatch(integer -> !Objects.isNull(integer) && integer > 0)) {
                    redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(ERROR_PIZZA_AMOUNT, null, locale));
                    return "redirect:/standardPizzas";
                }
                ingredientService.checkIfEnoughIngredientsForPizzas(pizzaCounter.getOrderedPizzas());
                cartService.putAllPizzasToCart(pizzaCounter, cart);
                model.addAttribute("cart", cart);
            }
        } catch (PizzaException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
        }


        return "redirect:/standardPizzas";
    }

    @GetMapping("/custom")
    public String showCustomPizzas() {
        return "integratedWithMenu:customPizzas";
    }
}