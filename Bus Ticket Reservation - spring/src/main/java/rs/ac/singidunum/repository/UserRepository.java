package rs.ac.singidunum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rs.ac.singidunum.data.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	List<User> findAll();
	
	@Query(value = "select user.user_id, user.username, role.role_name from user inner join role on user.user_id = role.user_id where user.email = ?1", nativeQuery =true)
	Object findUserWithRole(String email);
	
	User findByEmail(String email);
	
	@Query(value = "SELECT count(user_id) FROM user WHERE email = ? and password = ?;", nativeQuery = true)
	int authentication(String email, String password);
	
	User findByToken(String token);
}
