package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.UserEntity;
import eu.busi.martiastrid.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String userName);

}
