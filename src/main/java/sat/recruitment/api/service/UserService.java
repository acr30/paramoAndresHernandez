package sat.recruitment.api.service;

import sat.recruitment.api.controller.User;

import java.util.Map;

public interface UserService {

    Map<String, Object> createUser(User user);
}
