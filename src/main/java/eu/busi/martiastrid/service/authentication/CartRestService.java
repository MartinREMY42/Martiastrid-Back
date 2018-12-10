package eu.busi.martiastrid.service.authentication;

import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.PizzaQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartRestService {

    @Autowired
    private OrderDao orderDao;

    public List<PizzaQuantity> getUserCart(String username) {
        System.out.println("\n\n\n\n CartRestService.getUserCart : ");
        List<PizzaQuantity> cart = new ArrayList<>();

        try {
            Order order = orderDao.getOpenOrderForUser(username);
            if (!Objects.isNull(order.getOrderLines())) {
                order.getOrderLines().stream()
                        .filter(orderLine -> orderLine.getQuantity() > 0)
                        .forEach(l -> cart.add( new PizzaQuantity(l.getPizza(), l.getQuantity())));
            }
        } catch (PizzaDatabaseException e) {
            return new ArrayList<>();
        }
        return cart;
    }

    public List<PizzaQuantity> mergeToCurrentCart(List<PizzaQuantity> addedPizzas, String username) {
        List<PizzaQuantity> oldCart = this.getUserCart(username);
        for (PizzaQuantity newPq : addedPizzas) {
            int indexInOldCart = oldCart.indexOf(newPq);
            if (indexInOldCart == -1 ) {
                oldCart.add(newPq);
            } else {
                oldCart.set(indexInOldCart, new PizzaQuantity(
                        newPq.getPizza(),
                        newPq.getQuantity() + oldCart.get(indexInOldCart).getQuantity()));
            }
        }
        return oldCart;
    }
}
