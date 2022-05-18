package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

public class DummyUserRepository implements UserRepository {
    private final List<User> users;

    public DummyUserRepository(){
        users = new ArrayList<>();
    }

    @Override
    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public void deleteUser(Integer id) {
        for (User user: users){
            if (user.getId() == id){
                users.remove(user);
                break;
            }
        }
    }

    @Override
    public void updateUser(Integer id, User updatedUser) {
        for(int i = 0; i < users.size(); i++){
            if (users.get(i).getId() == id){
                users.set(i, updatedUser);
                break;
            }
        }
    }
    @Override
    public List<User> getAll() {
        return users;
    }

}