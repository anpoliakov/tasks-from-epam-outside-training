import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserBase {
    private static UserBase userBase;
    private List<User> baseUsers = new ArrayList<User>();

    private UserBase() {
    }

    public static UserBase getUserBase(){
        if (userBase == null) {
            synchronized (UserBase.class) {
                if (userBase == null){
                    userBase = new UserBase();
                }
            }
        }
        return userBase;
    }
}
