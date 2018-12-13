package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.OrderEntity;
import eu.busi.martiastrid.dataAccess.repository.OrderRepository;
import eu.busi.martiastrid.dataAccess.repository.UserRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderConverter providerConverter;

    @Autowired
    private UserRepository userRepository;

    public Order getById(Integer id) {
        return providerConverter.orderEntityToModel(orderRepository.getOne(id));
    }

    public Order save(Order order) {
        OrderEntity orderEntity = providerConverter.orderModelToEntity(order);
        orderEntity = orderRepository.save(orderEntity);
        return providerConverter.orderEntityToModel(orderEntity);
    }

    public Order getOpenOrderForUser(String username) throws PizzaDatabaseException {
        OrderEntity order = orderRepository.findByFkUserEqualsAndFkPaymentIsNull(
                userRepository.findByUsername(username)
        );
        if (Objects.isNull(order)) {
            throw new PizzaDatabaseException();
        }
        return providerConverter.orderEntityToModel(order);
    }

}
