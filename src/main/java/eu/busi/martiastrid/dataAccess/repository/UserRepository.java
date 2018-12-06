package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String userName);

}
