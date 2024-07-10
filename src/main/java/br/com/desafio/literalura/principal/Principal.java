package br.com.desafio.literalura.principal;

import br.com.desafio.literalura.classesdto.LivroDTO;
import br.com.desafio.literalura.classesdto.ResultadoDTO;
import br.com.desafio.literalura.model.Autor;
import br.com.desafio.literalura.model.Livro;
import br.com.desafio.literalura.repository.RepositoryAutor;
import br.com.desafio.literalura.repository.RepositoryLivro;
import br.com.desafio.literalura.service.ConsumoAPI;
import br.com.desafio.literalura.service.ConverterDados;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI api = new ConsumoAPI();
    private final ConverterDados conversor = new ConverterDados();
    private final String endereco = "http://gutendex.com/books/?search=";
    private List<Autor> autores;
    private List<Livro> livros;

    private final RepositoryLivro repositoryLivro;
    private final RepositoryAutor repositoryAutor;

    public Principal(RepositoryLivro repositoryLivro, RepositoryAutor repositoryAutor) {
        this.repositoryLivro = repositoryLivro;
        this.repositoryAutor = repositoryAutor;
    }

    public void menu() {
        int escolha = -1;
        while (escolha != 0) {
            System.out.println("\n**** Bem-vindo(a) ao LiterAlura! ****");
            String menu = """
                          Escolha uma das opções:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores de determinado ano
                    5 - Listar livros de idioma específico
                    0 - Sair 
                    """;

            System.out.println(menu);
            escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosNumDeterminadoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o título do livro: ");
        String buscaLivroPorNome = scanner.nextLine();
        String json = api.consumirApi(endereco + buscaLivroPorNome.replace(" ", "%20"));

        LivroDTO dadosLivros = conversor.obterDados(json, LivroDTO.class);

        if (dadosLivros.resultados() != null && !dadosLivros.resultados().isEmpty()) {
            ResultadoDTO livroBuscado = dadosLivros.resultados().get(0);
            Livro livro = new Livro(livroBuscado);
            System.out.println(livro);

            repositoryLivro.save(livro);
        } else {
            System.out.println("Nenhum livro encontrado...\n");
        }
    }

    private void listarLivrosRegistrados() {
        livros = repositoryLivro.findAll();

        if (livros.isEmpty()) {
            System.out.println("Não há livros registrados...\n");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        autores = repositoryAutor.findAll();

        if (autores.isEmpty()) {
            System.out.println("Não há autores registrados.\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosNumDeterminadoAno() {
        System.out.println("Digite o ano:");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        autores = repositoryAutor.findAllByAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Não existem autores registrados neste ano...\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Escolha um idioma:
                Português 
                Inglês 
                Espanhol 
                Francês 
                """);
        String idiomaEscolhido = scanner.nextLine().trim().toLowerCase();

        livros = repositoryLivro.findAllByIdioma(idiomaEscolhido);

        if (livros.isEmpty()) {
            System.out.println("Não existem livros neste idioma.\n");
        } else {
            livros.forEach(System.out::println);
        }
    }
}
