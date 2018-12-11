package eu.busi.martiastrid.service.restService;

import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<PizzaQuantity> increment(Pizza pizza, String username){
        List<PizzaQuantity> oldCart = this.getUserCart(username);
        int indexPizza = oldCart.indexOf(pizza);
        if (indexPizza != -1) {
            PizzaQuantity newPQ = oldCart.get(indexPizza);
            newPQ.setQuantity(newPQ.getQuantity() + 1);
            oldCart.set(indexPizza, newPQ);
        }
        return oldCart;
    }

    public List<PizzaQuantity> decrement(Pizza pizza, String username){
        List<PizzaQuantity> oldCart = this.getUserCart(username);
        int indexPizza = oldCart.indexOf(pizza);
        if (indexPizza != -1) {
            PizzaQuantity newPQ = oldCart.get(indexPizza);
            newPQ.setQuantity(newPQ.getQuantity() - 1);
            oldCart.set(indexPizza, newPQ);
        }
        return oldCart;
    }

    public List<PizzaQuantity> remove(Pizza pizza, String username){
        List<PizzaQuantity> oldCart = this.getUserCart(username);
        oldCart = oldCart.stream().filter( ipq -> !ipq.getPizza().equals(pizza)).collect(Collectors.toList());
        return oldCart;
    }
}
