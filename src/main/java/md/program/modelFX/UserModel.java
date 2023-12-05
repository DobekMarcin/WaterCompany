package md.program.modelFX;


import md.program.database.repository.UserRepository;

import java.sql.SQLException;

public class UserModel {

    private UserFX userFx = new UserFX();
    private UserRepository userRepository = new UserRepository();


    public Boolean checkUser() throws SQLException {
        Integer result = userRepository.checkUser(userFx.getLogin(), userFx.getPassword());
        return result == 1 ? true : false;
    }

    public void createAdmin() throws SQLException {
        Integer userCount = userRepository.getAllUserCount();
        if (userCount == 0) {
            userRepository.createAdmin("admin", "admin");
        }}
    public UserFX getUserFx() {
        return userFx;
    }

    public void setUserFx(UserFX userFx) {
        this.userFx = userFx;
    }
}
