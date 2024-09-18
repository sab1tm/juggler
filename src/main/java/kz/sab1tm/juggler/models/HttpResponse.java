package kz.sab1tm.juggler.models;

import javafx.scene.paint.Color;
import lombok.Data;
import okhttp3.MediaType;

@Data
public class HttpResponse {
    private Integer code;
    private String status;
    private Color statusColor;
    private String duration;
    private String size;
    private String body;
    private MediaType contentType;
}
