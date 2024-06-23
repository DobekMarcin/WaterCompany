package md.program.modelFX;

import md.program.database.repository.SettingsRepository;

import java.sql.SQLException;

public class SettingsModel {

    private SettingsRepository settingsRepository = new SettingsRepository();

    public void deleteDefaultYear() throws SQLException {
        settingsRepository.deleteYearSettings();
    }
    public Integer getDefaultYear() throws SQLException {
        return settingsRepository.getDefaultYear();
    }

    public void updateDefaultYear(Integer year) throws SQLException {
        settingsRepository.insertNewDefaultYear(year);
    }
}
