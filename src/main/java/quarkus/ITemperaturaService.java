package quarkus;

import java.util.List;
import java.util.Optional;

public interface ITemperaturaService {

    void addTemperatura(Temperatura t);

    List<Temperatura> obtenerTemperaturas();

    int maxima();

    boolean isEmpty();

    Optional<Temperatura> sacarTemperatura(String ciudad);
}
