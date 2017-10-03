package rs.ac.singidunum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.data.Role;
import rs.ac.singidunum.data.Role.RoleName;
import rs.ac.singidunum.data.User;
import rs.ac.singidunum.dto.TokenDto;
import rs.ac.singidunum.dto.UserDto;
import rs.ac.singidunum.repository.RoleRepository;
import rs.ac.singidunum.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public boolean authentication(String token) {
		User user = userRepository.findByToken(token);
		if (user != null && token.equals(user.getToken())) {
			return true;
		} else {
			return false;
		}
	}

	public TokenDto login(UserDto userDto) {
		User foundUser = userRepository.findByEmail(userDto.getEmail());
		if (foundUser != null) {
			Role role = roleRepository.findByUser(foundUser);
			if (role != null && passwordEncoder.matches(userDto.getPassword(), foundUser.getPassword())) {
				foundUser.setActive(true);
				userRepository.save(foundUser);
				return new TokenDto(foundUser.getToken(), foundUser.getUsername(), role.getName().toString());
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	public boolean logout(String token) {
		User foundUser = userRepository.findByToken(token);
		if (foundUser != null) {
			foundUser.setActive(false);
			userRepository.save(foundUser);
			return true;
		} else {
			return false;
		}
	}

	public User signupUser(UserDto userDto) {
		if (userRepository.findByEmail(userDto.getEmail()) == null) {

			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userDto.setToken(passwordEncoder.encode(userDto.getUsername() + ":" + userDto.getPassword()));

			User savedUser = userRepository.save(new User(userDto));
			roleRepository.save(new Role(RoleName.USER, savedUser));
			return savedUser;
		} else {
			return null;
		}
	}
	
	public List<UserDto> findUsers() {
		
		List<User> foundUsers = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for(User user : foundUsers) {
			userDtos.add(new UserDto(user));
		}
		return userDtos;
	}

}
