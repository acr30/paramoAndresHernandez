package sat.recruitment.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.domain.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceBean implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceBean.class);

    private List<User> users = new ArrayList<User>();

    public Map<String, Object> createUser(User userToCreate) {

        Map<String, Object> customMap = new HashMap<>();

        try {

            User newUser = new User();
            newUser.setName(userToCreate.getName());
            newUser.setEmail(userToCreate.getEmail());
            newUser.setAddress(userToCreate.getAddress());
            newUser.setPhone(userToCreate.getPhone());
            newUser.setUserType(userToCreate.getUserType());
            newUser.setMoney(userToCreate.getMoney());

            if (newUser.getUserType().equals("Normal")) {
                if (Double.valueOf(newUser.getMoney()) > 100) {
                    Double percentage = Double.valueOf("0.12");
                    // If new user is normal and has more than USD100
                    Double gif = Double.valueOf(newUser.getMoney()) * percentage;
                    newUser.setMoney(newUser.getMoney() + gif);
                }
                if (Double.valueOf(newUser.getMoney()) < 100) {
                    if (Double.valueOf(newUser.getMoney()) > 10) {
                        Double percentage = Double.valueOf("0.8");
                        Double gif = Double.valueOf(newUser.getMoney()) * percentage;
                        newUser.setMoney(newUser.getMoney() + gif);
                    }
                }
            }
            if (newUser.getUserType().equals("SuperUser")) {
                if (Double.valueOf(newUser.getMoney()) > 100) {
                    Double percentage = Double.valueOf("0.20");
                    Double gif = Double.valueOf(newUser.getMoney()) * percentage;
                    newUser.setMoney(newUser.getMoney() + gif);
                }
            }
            if (newUser.getUserType().equals("Premium")) {
                if (Double.valueOf(newUser.getMoney()) > 100) {
                    Double gif = Double.valueOf(newUser.getMoney()) * 2;
                    newUser.setMoney(newUser.getMoney() + gif);
                }
            }

            InputStream fstream;
            try {
                fstream = getClass().getResourceAsStream("/users.txt");

                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;

                while ((strLine = br.readLine()) != null) {
                    String[] line = strLine.split(",");
                    User user = new User();
                    user.setName(line[0]);
                    user.setEmail(line[1]);
                    user.setPhone(line[2]);
                    user.setAddress(line[3]);
                    user.setUserType(line[4]);
                    user.setMoney(Double.valueOf(line[5]));
                    users.add(user);

                }
                fstream.close();
            } catch (Exception e) {
                logger.error("createUser-Exception", e);
                customMap.put("status", "ERROR");
                customMap.put("message", e.getMessage());
                return customMap;
            }

            Boolean isDuplicated = false;
            for (User user : users) {

                if (user.getEmail().equals(newUser.getEmail()) || user.getPhone().equals(newUser.getPhone())) {
                    isDuplicated = true;
                } else if (user.getName().equals(newUser.getName())) {
                    if (user.getAddress().equals(newUser.getAddress())) {
                        isDuplicated = true;
                    }
                }
            }
            if (isDuplicated) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is duplicated");
            } else {
                users.add(newUser);

                //Se podria guardar en el archivo
            }

            logger.info("createUser-Ok");
            customMap.put("status", "OK");
            customMap.put("message", "User created");
            return customMap;
        } catch (Exception e) {
            logger.error("createUser-Exception", e);
            customMap.put("status", "ERROR");
            customMap.put("message", e.getMessage());
            return customMap;
        }
    }
}
