package quarkus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperatura {

    private String ciudad;
    private int minima;
    private int maxima;

}
