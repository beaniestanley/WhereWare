package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.service.UserService;

public class UserViewModel extends AndroidViewModel {

    private final UserService userService;
    private final Executor executor;

    private final MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

    public UserViewModel(Application application){
        super(application);
        WhereWareApplication whereWareApplication = (WhereWareApplication) getApplication();
        userService = whereWareApplication.getUserService();
        executor = whereWareApplication.getBackgroundExecutor();
    }

    public LiveData<List<User>> getUserLiveData() {return userLiveData;}

    public void loadData() {
        executor.execute(() -> {
            List<User> users = userService.getAll();
            this.userLiveData.postValue(users);
        });
    }

    public void createUser(String firstName, String lastName) {
        executor.execute(() -> {
            userService.addUser(firstName, lastName);
            List<User> users = userService.getAll();
            this.userLiveData.postValue(users);
        });
    }
    public void removeUser(Integer id) {
        executor.execute(() -> {
            userService.removeUser(id);
            List<User> users = userService.getAll();
            this.userLiveData.postValue(users);
        });
    }
    public void updateUser(Integer id, String firstName, String lastName) {
        executor.execute(() -> {
            userService.updateUser(id, firstName, lastName);
            List<User> users = userService.getAll();
            this.userLiveData.postValue(users);
        });
    }
}
