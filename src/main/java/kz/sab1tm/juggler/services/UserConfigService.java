package kz.sab1tm.juggler.services;

import javafx.scene.Parent;
import kz.sab1tm.juggler.models.enums.AppThemeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static kz.sab1tm.juggler.utils.ResourceUtil.getResourceAsString;

public class UserConfigService {

    private static final Logger log = LoggerFactory.getLogger(UserConfigService.class);

    public void applyUserUIConfig(Parent parent) {
        setUserUITheme(parent, getUserUITheme().name());
    }

    public AppThemeEnum getUserUITheme() {
        // TODO: getting from data storage
        return AppThemeEnum.Light;
    }

    public void setUserUITheme(Parent parent, String themeName) {
        log.info("Setting user UI theme to {}", themeName);
        AppThemeEnum theme = AppThemeEnum.valueOf(themeName);
        parent.getStylesheets().clear();
        parent.getStylesheets().add(getResourceAsString(theme.getFilePath()));
    }
}
