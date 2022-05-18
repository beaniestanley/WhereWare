package at.tugraz.software22.service;

import java.util.List;

import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void addUser(String firstName, String lastName) {
        userRepository.addUser(firstName, lastName);
    }

    public void removeUser(Integer id) {
        userRepository.deleteUser(id);
    }

    public void updateUser(Integer id, String firstName, String lastName) {
        userRepository.updateUser(id, firstName, lastName);
    }
}
