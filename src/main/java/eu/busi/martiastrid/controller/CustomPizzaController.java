package eu.busi.martiastrid.controller;

import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.form.IngredientsList;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static eu.busi.martiastrid.constants.Constants.*;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_MESSAGE;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_NOT_ENOUGH_INGREDIENTS_IN_STOCK;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_PIZZA_AMOUNT;

@Controller
@RequestMapping("/customPizza")
@SessionAttributes({CART})
public class CustomPizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("cart")
    public HashMap<Pizza, Integer> setupCart() {
        return new HashMap<>();
    }

    @GetMapping
    public String showCustomPizzas(Model model) {

        model.addAttribute(ALL_INGREDIENTS_TO_BE_SELECTED, ingredientService.getAllFromDatabase());
        model.addAttribute(CHOSEN_INGREDIENTS, new IngredientsList());
        return "integratedWithMenu:customPizzas";
    }

    @PostMapping("/add")
    public String addPizza(HttpServletRequest request,
                           Model model,
                           @ModelAttribute(value = ALL_INGREDIENTS_TO_BE_SELECTED) IngredientsList allIngredientsSelected,
                           @SessionAttribute("cart") HashMap<Pizza, Integer> cart,
                           Locale locale,
                           final RedirectAttributes redirectAttributes) {
        int quantity = allIngredientsSelected.getQuantity();

        if (quantity < 0) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(ERROR_PIZZA_AMOUNT, null, locale));
            return "redirect:/customPizza";
        }

        if (!ingredientService.checkEnoughIngredients(allIngredientsSelected.getIngredients() , quantity)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(ERROR_NOT_ENOUGH_INGREDIENTS_IN_STOCK, null, locale));
            return "redirect:/customPizza";
        }

        boolean isUserConnected = ! Objects.isNull(request.getUserPrincipal());
        try {
            Pizza customPizza = pizzaService.createCustomPizza(allIngredientsSelected.getIngredients());

            if (! isUserConnected) {
                cartService.addCustomPizzasToCart(customPizza, cart, quantity);
            } else {
                Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                Pizza pizza = pizzaService.savePizzaInDatabase(customPizza);
                order.addLine(pizza, quantity);
                orderService.saveOrderInDatabase(order);
                model.addAttribute("cart", cartService.loadCartFromDatabase());
            }
            model.addAttribute(ERROR_MESSAGE, null);
        } catch (PizzaException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
        }

        return "redirect:/customPizza";
    }
}
