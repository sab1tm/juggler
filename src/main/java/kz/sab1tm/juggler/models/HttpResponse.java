package kz.sab1tm.juggler.models;

import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class HttpResponse {
    private Integer code;
    private String status;
    private Color statusColor;
    private Long duration;
    private Long size;
    private String body;
}
