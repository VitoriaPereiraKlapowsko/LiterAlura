package src.main.java.br.com.desafio.literalura.classesdto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import src.main.java.br.com.desafio.literalura.model.Autor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoDTO(@JsonAlias("title") String titulo,
                        @JsonAlias("authors") List<Autor> autor,
                        @JsonAlias("languages") List<String> idioma,
                        @JsonAlias("download_count") Integer numeroDownloads) {
}
