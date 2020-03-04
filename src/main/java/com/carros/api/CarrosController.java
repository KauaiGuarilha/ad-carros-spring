package com.carros.api;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.service.CarroService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired CarroService service;

    @GetMapping()
    public ResponseEntity<List<CarroDTO>> get() {
        // return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        // Optional<CarroDTO> carro = service.getCarroById(id);
        // return carro.isPresent() ? ResponseEntity.ok(carro.get()) :
        // ResponseEntity.notFound().build();

        CarroDTO carro = service.getCarroById(id);
        return ResponseEntity.ok(carro); // Tratamento efetuado em CarroService com o Exception
    }

    // Sempre que retornar um Array vazio,uma lista vazia.. Podemos fazer da seguinte situação
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarroByTipo(tipo);

        // Sempre que a lista de carros retornar vazia, ele vai ocorrer um noContent 204

        return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"}) // Permissão de rest apenas para ADMIN
    public ResponseEntity insertCar(@RequestBody Carro carro) {

        CarroDTO c = service.insert(carro);
        // O tratamento será feito pelo Exception
        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();

        /*try{
            CarroDTO c = service.insert(carro);

            URI location = getUri(c.getId()); // Retornará a url que foi salvo
            return ResponseEntity.created(location).build();  //ResponseEntity.created retorna 201 create
        }catch (Exception e){
            return ResponseEntity.badRequest().build(); // caso ocorra algum problema, retorna BadRequest
        }*/
    }

    // Irá retornar assim que for gravado POST o parâmetro
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable("id") Long id, @RequestBody Carro carro) {

        carro.setId(id);

        CarroDTO car = service.update(carro, id);

        return car != null
                ? // se por acaso ele for diferente de null, irá retornar 200 Ok
                ResponseEntity.ok(car)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();

        /*boolean ok = service.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();*/
    }
}
