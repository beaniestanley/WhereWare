package at.tugraz.software22.domain;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    void addUser(String firstName, String lastName);

    void deleteUser(Integer id);

    void updateUser(Integer id, String firstName, String lastName);

}
