package com.carros.repository;

import com.carros.domain.Carro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    List<Carro> findByTipo(String tipo);
}
