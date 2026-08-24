package estudo.spring_java;

import estudo.spring_java.model.Produto;
import estudo.spring_java.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJavaApplication.class, args);
	}

	@Bean
	public CommandLineRunner carregarDados(ProdutoRepository repository) {
		return args -> {
			repository.save(new Produto("Notebook", 4000.0));
			repository.save(new Produto("Mouse", 300.0));
			repository.save(new Produto("Teclado", 500.0));
			System.out.println("Produtos salvos no banco com sucesso!");
		};
	}

}
