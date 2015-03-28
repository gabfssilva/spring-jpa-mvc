package br.com.spring.jpa.mvc.example.repositories;

import br.com.spring.jpa.mvc.example.entities.User;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Component
public class UserRepository extends JpaRepository<User, Long> {

    public User find(String username, String password){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery(User.FIND_BY_USERNAME_AND_PASSWORD, User.class);

        namedQuery.setParameter("username", username);
        namedQuery.setParameter("password", password);

        List<User> resultList = namedQuery.getResultList();

        if(resultList == null || resultList.isEmpty()){
            return null;
        }

        return resultList.get(0);
    }
}
