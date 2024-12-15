package ar.ramos.girsureceiver.internal;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class PositionRecord {
    private Long id;

    private long truckId;
    private double latitude;
    private double longitude;
    private long timestamp; // in milliseconds since epoch
}
