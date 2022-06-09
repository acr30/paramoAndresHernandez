package sat.recruitment.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.domain.User;
import sat.recruitment.api.service.UserService;

@RestController
@RequestMapping(value = "/api/v1")
public class SatRecruitmentController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> createUser(@RequestBody User messageBody) {
		String errors = "";

		errors = validateErrors(messageBody.getName(), messageBody.getEmail(), messageBody.getAddress(), messageBody.getPhone(), messageBody.getUserType());

		if (!"".equals(errors)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors);
		}

		Map<String, Object> jsonResponce = userService.createUser(messageBody);
		String status = (String) jsonResponce.get("status");

		if ("OK".equals(status)) {
			return new ResponseEntity<>(jsonResponce, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(jsonResponce, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public String validateErrors(String name, String email, String address, String phone, String userType) {

		String nameError = "";
		String emailError = "";
		String addressError = "";
		String phoneError = "";
		String userTypeError = "";

		if (name == null || "".equals(name))
			// Validate if Name is null
			nameError = "The name is required";
		if (email == null || "".equals(email))
			// Validate if Email is null
			emailError = " The email is required";
		if (address == null || "".equals(address))
			// Validate if Address is null
			addressError = " The address is required";
		if (phone == null || "".equals(phone))
			// Validate if Phone is null
			phoneError = " The phone is required";
		if (userType == null || "".equals(userType))
			// Validate if Phone is null
			userTypeError = " The user type is required";

		return nameError + emailError + addressError + phoneError + userTypeError;
	}

}
