package estudo.spring_java.repository;

import estudo.spring_java.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
        // Só isso, o Spring já saber fazer o SELECT, INSERT, UPDATE e DELETE.
}
