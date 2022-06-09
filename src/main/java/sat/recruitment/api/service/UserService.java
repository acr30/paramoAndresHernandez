package sat.recruitment.api.service;

import sat.recruitment.api.domain.User;

import java.util.Map;

public interface UserService {

    Map<String, Object> createUser(User user);
}
