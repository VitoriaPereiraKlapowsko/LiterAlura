package br.com.desafio.literalura;

import br.com.desafio.literalura.principal.Principal;
import br.com.desafio.literalura.repository.RepositoryAutor;
import br.com.desafio.literalura.repository.RepositoryLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private RepositoryLivro repositoryLivro;

    @Autowired
    private RepositoryAutor repositoryAutor;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositoryLivro, repositoryAutor);
        principal.menu();
    }
}
