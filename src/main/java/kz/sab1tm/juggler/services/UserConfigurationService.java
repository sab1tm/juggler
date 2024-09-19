package kz.sab1tm.juggler.services;

import javafx.scene.Parent;
import kz.sab1tm.juggler.models.enums.AppThemeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static kz.sab1tm.juggler.utils.ResourceUtil.getResourceAsString;

public class UserConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(UserConfigurationService.class);

    public void applyUserUiSettings(Parent parent) {
    }

    public AppThemeEnum getCurrentUITheme() {
        return AppThemeEnum.Dark;
    }

    public void changeUITheme(Parent parent, String themeName) {
        AppThemeEnum theme = AppThemeEnum.valueOf(themeName);
        parent.getStylesheets().clear();
        parent.getStylesheets().add(getResourceAsString(theme.getFilePath()));
    }
}
