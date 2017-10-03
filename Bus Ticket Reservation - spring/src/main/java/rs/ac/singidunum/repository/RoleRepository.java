package rs.ac.singidunum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rs.ac.singidunum.data.Role;
import rs.ac.singidunum.data.User;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	@Query("SELECT new Role(r.roleName) FROM Role r WHERE r.user = ?1")
	Role findByUser(User user);
}
