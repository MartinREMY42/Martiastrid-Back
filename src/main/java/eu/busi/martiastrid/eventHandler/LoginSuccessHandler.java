package eu.busi.martiastrid.eventHandler;

import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.CartService;
import eu.busi.martiastrid.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        HashMap<Pizza, Integer> cartFromDb = null;
        try {
            cartFromDb = cartService.loadCartFromDatabase();
        } catch (PizzaException e) {
            // try to create an order if there is no present
            orderService.createOrderForConnectedUser();
            try {
                cartFromDb = cartService.loadCartFromDatabase();
            } catch (PizzaException e1) { }
        }


        try {
            HashMap<Pizza, Integer> cartFromSession = (HashMap<Pizza, Integer>) httpServletRequest.getSession().getAttribute("cart");
            if (!Objects.isNull(cartFromSession)) {
                // merge
                // persist cart from session
                Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
                // todo: find out why there is a bug when not explicitely persisting pizzas
                for (Pizza pizzaEntry : cartFromSession.keySet()) {
                    Pizza p = pizzaDao.save(pizzaEntry);
                    order.addPizza(p, cartFromSession.get(pizzaEntry));
                }
                //
                //order.addPizzas(cartFromSession);
                orderDao.save(order);
                httpServletRequest.getSession().setAttribute("cart", cartService.loadCartFromDatabase());
            } else if (!Objects.isNull(cartFromDb)) {
                // put cart from db to session
                httpServletRequest.getSession().setAttribute("cart", cartFromDb);
            }
        } catch (PizzaException e) { }

        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);

    }
}
