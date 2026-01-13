package hebertesteves.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAno(@JsonAlias("codigo") String codigo,
                       @JsonAlias("nome") String nome) {
}
