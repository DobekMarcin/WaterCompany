package md.program.modelFX;

import md.program.database.repository.UserRepository;

import java.sql.SQLException;

public class ChangePasswordModel {

    private ChangePasswordFX changePasswordFX = new ChangePasswordFX();
    private String description;
    private UserRepository userRepository = new UserRepository();

    public Integer checkPassword() throws SQLException {
        Integer result = userRepository.checkPassword(changePasswordFX.getOldPassword());
        return result;
    }

    public Boolean changePassword() throws SQLException {
        Integer newPassword = checkPassword();
        if (newPassword > 0 && (changePasswordFX.getNewPasswordAccept().equals(changePasswordFX.getNewPassword()))) {
            userRepository.changePassword(changePasswordFX.getNewPasswordAccept());
            return true;
        }
        return false;
    }

    public ChangePasswordFX getChangePasswordFX() {
        return changePasswordFX;
    }

    public void setChangePasswordFX(ChangePasswordFX changePasswordFX) {
        this.changePasswordFX = changePasswordFX;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
