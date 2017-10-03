package rs.ac.singidunum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.data.User;
import rs.ac.singidunum.dto.TokenDto;
import rs.ac.singidunum.dto.UserDto;
import rs.ac.singidunum.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		User user = userService.signupUser(userDto);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		TokenDto tokenDto = userService.login(userDto);
		if (tokenDto != null && tokenDto.getRoleName().equals("USER")) {
			return new ResponseEntity<>(tokenDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public ResponseEntity<?> adminLogin(@RequestBody UserDto userDto) {
		TokenDto tokenDto = userService.login(userDto);
		if (tokenDto != null && tokenDto.getRoleName().equals("ADMIN")) {
			return new ResponseEntity<>(tokenDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
	public ResponseEntity<?> adminLogout(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			if (userService.logout(token)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public ResponseEntity<?> userList(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			List<UserDto> foundUsers = userService.findUsers();
			if (foundUsers != null) {
				return new ResponseEntity<>(foundUsers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<?> logout(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			if (userService.logout(token)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
