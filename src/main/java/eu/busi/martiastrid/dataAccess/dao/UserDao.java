package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.UserEntity;
import eu.busi.martiastrid.dataAccess.repository.UserRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_USERNAME_TAKEN;

@Service
@Transactional
public class UserDao {

    @Autowired
    private ProviderConverter providerConverter;

    @Autowired
    private UserRepository userRepository;


    public User saveNewUser(User user) throws PizzaDatabaseException {
        if (!Objects.isNull(userRepository.findByUsername(user.getUsername()))) {
            throw new PizzaDatabaseException(ERROR_USERNAME_TAKEN);
        }
        UserEntity userEntity = providerConverter.userModelToEntity(user);
        userEntity = userRepository.save(userEntity);
        return providerConverter.userEntityToModel(userEntity);
    }

    public User getByUsername(String username) {
        return providerConverter.userEntityToModel(userRepository.findByUsername(username));
    }

}
