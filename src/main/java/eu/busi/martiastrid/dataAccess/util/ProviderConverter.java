package eu.busi.martiastrid.dataAccess.util;

import eu.busi.martiastrid.dataAccess.entity.*;
import eu.busi.martiastrid.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProviderConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Pizza entityToModel(PizzaEntity pizzaEntity){
        return new Pizza(
          pizzaEntity.getId(),
          pizzaEntity.getGenericName(),
          pizzaEntity.getPrice()
        );
    }

    public PizzaEntity pizzaToEntity(Pizza pizza) {
        return new PizzaEntity(
            pizza.getGenericName(),
            pizza.getPrice()
        );
    }

    public Recipe recipeEntityToModel(RecipeEntity recipeEntity) {
        return new Recipe(recipeEntity.getId(),
                recipeEntity.getQuantity(),
                ingredientEntityToModel(recipeEntity.getIngredientEntity()));
    }

    public RecipeEntity recipeModelToEntity(Recipe recipe, PizzaEntity pizzaEntity) {
        return  new RecipeEntity(recipe.getId(),
                recipe.getQuantity(),
                pizzaEntity,
                ingredientModelToEntity(recipe.getIngredient()));
    }

    public Ingredient ingredientEntityToModel(IngredientEntity ingredientEntity) {
        return new Ingredient(ingredientEntity.getId(),
                ingredientEntity.getGenericName(),
                ingredientEntity.getStockQuantity(),
                ingredientEntity.getPriceForComposition());
    }

    public IngredientEntity ingredientModelToEntity(Ingredient ingredient) {
        return new IngredientEntity(
                ingredient.getId(),
                ingredient.getGenericName(),
                ingredient.getStockQuantity(),
                ingredient.getPriceForComposition()
        );
    }

    private Order orderEntityToModelWithUser(OrderEntity orderEntity, User user) {
        return new Order(
                orderEntity.getId(),
                orderEntity.getCreationDate(),
                orderEntity.getPromoName(),
                orderEntity.getReduction(),
                orderEntity.getOrderLineCollection().stream().map(this::orderLineEntityToModel).collect(Collectors.toList()),
                user,
                Objects.isNull(orderEntity.getFkPayment()) ? null : paymentEntityToModel(orderEntity.getFkPayment())
        );
    }

    public Order orderEntityToModel(OrderEntity orderEntity) {
        Order order = new Order(
                orderEntity.getId(),
                orderEntity.getCreationDate(),
                orderEntity.getPromoName(),
                orderEntity.getReduction(),
                Objects.isNull(orderEntity.getOrderLineCollection())? null :orderEntity.getOrderLineCollection().stream().map(this::orderLineEntityToModel).collect(Collectors.toList()),
                userEntityToModel(orderEntity.getFkUser()),
                Objects.isNull(orderEntity.getFkPayment()) ? null : paymentEntityToModel(orderEntity.getFkPayment())
        );
        return order;
    }

    private OrderEntity orderModelToEntityWithUser(Order order, UserEntity userEntity) {
        OrderEntity orderEntity = new OrderEntity(
                order.getId(),
                order.getCreationDate(),
                order.getPromoName(),
                order.getReduction(),
                null,
                Objects.isNull(order.getPayment()) ? null : paymentModelToEntity(order.getPayment()),
                userEntity

        );
        orderEntity.setOrderLineCollection(
                order.getOrderLines().stream().map(l -> orderLineModelToEntity(l, orderEntity)).collect(Collectors.toList())
        );
        return orderEntity;
    }

    public OrderEntity orderModelToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity(
                order.getId(),
                order.getCreationDate(),
                order.getPromoName(),
                order.getReduction(),
                null,
                Objects.isNull(order.getPayment()) ? null : paymentModelToEntity(order.getPayment()),
                userModelToEntity(order.getUser()));
        orderEntity.setOrderLineCollection(
                Objects.isNull(order.getOrderLines())? null :order.getOrderLines().stream()
                        .map(l -> orderLineModelToEntity(l, orderEntity))
                        .collect(Collectors.toList()));
        return orderEntity;
    }

    private OrderLineEntity orderLineModelToEntity(OrderLine orderLine, OrderEntity orderEntity) {
        return new OrderLineEntity(
                orderLine.getId(),
                orderLine.getQuantity(),
                orderEntity,
                pizzaModelToEntity(orderLine.getPizza()));

    }

    private OrderLine orderLineEntityToModel(OrderLineEntity orderLineEntity) {
        return new OrderLine(
                orderLineEntity.getId(),
                orderLineEntity.getQuantity(),
                pizzaEntityToModel(orderLineEntity.getFkPizza()));
    }

    private Payment paymentEntityToModel(PaymentEntity fkPayment) {
        return new Payment(
                fkPayment.getId(),
                fkPayment.getDate(),
                fkPayment.getRefPaypal(),
                fkPayment.getTotal()
        );
    }

    private PaymentEntity paymentModelToEntity(Payment payment) {
        return new PaymentEntity(
                payment.getId(),
                payment.getDate(),
                payment.getRefPaypal(),
                payment.getSum()
        );
    }

    public Pizza pizzaEntityToModel(PizzaEntity pizzaEntity) {
        return new Pizza(pizzaEntity.getId(),
                pizzaEntity.getGenericName(),
                pizzaEntity.getPrice(),
                pizzaEntity.getRecipeEntities().stream().map(this::recipeEntityToModel).collect(Collectors.toList()),
                Objects.isNull(pizzaEntity.getCategoryEntity())? null :pizzaEntity.getCategoryEntity().stream().map(CategoryEntity::getCategory).collect(Collectors.toSet()));
    }

    public PizzaEntity pizzaModelToEntity(Pizza pizza) {
        PizzaEntity pizzaEntity = new PizzaEntity(
                pizza.getId(),
                pizza.getGenericName(),
                pizza.getPrice(),
                null,
                Objects.isNull(pizza.getCategory()) ? null : pizza.getCategory().stream()
                        .map(CategoryEntity::new)
                        .collect(Collectors.toSet()));
        pizzaEntity.setRecipeEntities(
                Objects.isNull(pizza.getRecipes())? null : pizza.getRecipes().stream()
                        .map(recipe -> recipeModelToEntity(recipe, pizzaEntity))
                        .collect(Collectors.toList()));
        return pizzaEntity;
    }

    public UserEntity userModelToEntity(User userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        UserEntity userEntity = new UserEntity(
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getEnabled(),
                userModel.getNonExpired(),
                userModel.getNonLocked(),
                userModel.getCredentialsNonExpired(),
                userModel.getCreationDate(),
                userModel.getBirthDate(),
                userModel.getAuthorities().stream().map(a -> new AuthorityEntity(a.getAuthority())).collect(Collectors.toSet()),
                null
        );
        userEntity.setOrdersCollection(Objects.isNull(userModel.getOrders()) ? null :userModel.getOrders().stream().map(order -> orderModelToEntityWithUser(order, userEntity)).collect(Collectors.toSet()));
        userEntity.setPizzasFavorites(Objects.isNull(userModel.getPizzasFavorites()) ? null : userModel.getPizzasFavorites().stream().map( pizza -> pizzaModelToEntity(pizza)).collect(Collectors.toSet()));

        return userEntity;
    }

    public User userEntityToModel(UserEntity userEntity) {
        User user = new User(
                userEntity.getUsername(),
                "",
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isAccountNonLocked(),
                userEntity.isCredentialsNonExpired(),
                userEntity.getAuthorities()
                        .stream()
                        .map(
                                a -> new Authority(a.getAuthority()))
                        .collect(Collectors.toList()),
                userEntity.getBirthDate(),
                userEntity.getCreationDate(),
                null
        );
        user.setOrders(
                Objects.isNull(userEntity.getOrdersCollection()) ?
                        null :
                        userEntity
                                .getOrdersCollection()
                                .stream()
                                .map(orderEntity -> orderEntityToModelWithUser(orderEntity, user))
                                .collect(Collectors.toList())
        );
        user.setPizzasFavorites(
                Objects.isNull(userEntity.getPizzasFavorites()) ?
                        null : userEntity.getPizzasFavorites()
                        .stream()
                        .map(pizzaEntity -> pizzaEntityToModel(pizzaEntity))
                        .collect(Collectors.toSet())
        );

        return user;
    }

    public HashMap<Integer, Integer> pizzaQuantityToPizzaCounter(List<PizzaQuantity> pizzaQuantities) {
        HashMap<Integer, Integer> pizzaCounter = new HashMap<>();
        pizzaQuantities.forEach( pq -> {
            pizzaCounter.put(pq.getPizza().getId(), pq.getQuantity());
        });
        return pizzaCounter;
    }
}