package kz.sab1tm.juggler.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppThemeEnum {
    Dark("/css/dark.css"),
    Light("/css/light.css");

    private final String filePath;
}
