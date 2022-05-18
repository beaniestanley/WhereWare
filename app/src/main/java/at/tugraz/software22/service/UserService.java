package at.tugraz.software22.service;

import java.util.List;

import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }
    public void removeUser(Integer id){
        userRepository.deleteUser(id);
    }
    public void updateUser(Integer id, User user){
        userRepository.updateUser(id, user);
    }
}
