package at.tugraz.software22.domain;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    void addUser(User newUser);

    void deleteUser(Integer id);

    void updateUser(Integer id, User updatedUser);

}
