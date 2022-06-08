package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

public class DummyUserRepository implements UserRepository {
    private final List<User> users;

    public DummyUserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public void addUser(String firstName, String lastName) {
        User user = new User(users.size() + 1, firstName, lastName);
        users.add(user);
    }

    @Override
    public void deleteUser(Integer id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                break;
            }
        }
    }

    @Override
    public void updateUser(Integer id, String firstName, String lastName) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.get(i).setFirstName(firstName);
                users.get(i).setLastName(lastName);
                break;
            }
        }
    }

    @Override
    public List<User> getAll() {
        return users;
    }

}