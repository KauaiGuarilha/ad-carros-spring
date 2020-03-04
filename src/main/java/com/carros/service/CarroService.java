package com.carros.service;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.domain.dto.exception.ObjectNotFoundException;
import com.carros.repository.CarroRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {

    @Autowired CarroRepository repository;

    public List<CarroDTO> getCarros() {
        return repository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        return repository.findByTipo(tipo).stream()
                .map(CarroDTO::create)
                .collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        // return repository.findById(id).map(CarroDTO::create); // Conversão de DTO pra Optional
        return repository
                .findById(id)
                .map(CarroDTO::create)
                .orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public Carro save(Carro carro) {
        return repository.save(carro);
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o Registro!");

        return CarroDTO.create(repository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> optional = repository.findById(id);

        if (optional.isPresent()) {
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            repository.save(db);

            return CarroDTO.create(db);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
