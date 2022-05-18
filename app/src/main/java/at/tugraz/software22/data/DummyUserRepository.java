package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

public class DummyUserRepository implements UserRepository {
    private final List<User> users;

    DummyUserRepository(){
        users = new ArrayList<>();
    }

    @Override
    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public void deleteUser(Integer id) {
        users.remove(id - 1);
    }

    @Override
    public void updateUser(Integer id, User updatedUser) {
        users.set(id - 1, updatedUser);
    }
    @Override
    public List<User> getAll() {
        return users;
    }

}