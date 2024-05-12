package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;

public class ChangePasswordFX {

    private SimpleStringProperty oldPassword = new SimpleStringProperty();
    private SimpleStringProperty newPassword = new SimpleStringProperty();
    private SimpleStringProperty newPasswordAccept = new SimpleStringProperty();

    public ChangePasswordFX(SimpleStringProperty oldPassword, SimpleStringProperty newPassword, SimpleStringProperty newPasswordAccept) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordAccept = newPasswordAccept;
    }

    public ChangePasswordFX() {
    }

    public String getOldPassword() {
        return oldPassword.get();
    }

    public SimpleStringProperty oldPasswordProperty() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword.set(oldPassword);
    }

    public String getNewPassword() {
        return newPassword.get();
    }

    public SimpleStringProperty newPasswordProperty() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword.set(newPassword);
    }

    public String getNewPasswordAccept() {
        return newPasswordAccept.get();
    }

    public SimpleStringProperty newPasswordAcceptProperty() {
        return newPasswordAccept;
    }

    public void setNewPasswordAccept(String newPasswordAccept) {
        this.newPasswordAccept.set(newPasswordAccept);
    }
}
