package eu.busi.martiastrid.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Order implements Serializable {
    private Integer id;
    private Date creationDate;
    private String promoName;
    private int reduction;
    private Collection<OrderLine> orderLines;
    private User user;
    private Payment payment;

    public Order() {
    }

    public Order(Integer id, Date creationDate, String promoName,
                 int reduction, Collection<OrderLine> orderLines,
                 User user, Payment payment) {
        this.id = id;
        this.creationDate = creationDate;
        this.promoName = promoName;
        this.reduction = reduction;
        this.orderLines = orderLines;
        this.user = user;
        this.payment = payment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public Collection<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Collection<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void incrementPizza(Integer id) {
        if (orderLines.stream().anyMatch(o -> o.getPizza().getId().equals(id))) {
            OrderLine orderLine = orderLines.stream().filter(l -> l.getPizza().getId().equals(id)).findFirst().get();
            orderLine.setQuantity(orderLine.getQuantity() + 1);
        }
    }

    public void decrementPizza(Integer id) {
        if (orderLines.stream().anyMatch(l -> l.getPizza().getId().equals(id))) {
            OrderLine orderLine = orderLines.stream().filter(l -> l.getPizza().getId().equals(id)).findFirst().get();
            int newquantity = orderLine.getQuantity() - 1;
            orderLine.setQuantity(newquantity);
        }
    }

    public void removePizza(Integer id) {
        orderLines = orderLines.stream()
                .filter(ol -> !ol.getPizza().getId().equals(id))
                .collect(Collectors.toList());
    }

    public void addPizza(Pizza pizza, Integer quantity) {
        if (Objects.isNull(orderLines)) {
            orderLines = new ArrayList<>();
        }
        Optional<OrderLine> orderLine = orderLines.stream().filter(orderLine1 -> orderLine1.getPizza().equals(pizza)).findFirst();
        if (orderLine.isPresent()) {
            orderLine.get().setQuantity(orderLine.get().getQuantity() + quantity);
        } else {
            OrderLine neworderLine = new OrderLine(null, quantity, pizza);
            orderLines.add(neworderLine);
        }
    }

    public void addLine(Pizza pizza, Integer quantity) {
        if (Objects.isNull(orderLines)) {
            orderLines = new ArrayList<>();
        }
        OrderLine neworderLine = new OrderLine(null, quantity, pizza);
        orderLines.add(neworderLine);
    }
}
