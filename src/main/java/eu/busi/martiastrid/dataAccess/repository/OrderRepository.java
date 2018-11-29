package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.OrderEntity;
import eu.busi.martiastrid.dataAccess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findByFkUserEqualsAndFkPaymentIsNull(UserEntity userEntity);
}
