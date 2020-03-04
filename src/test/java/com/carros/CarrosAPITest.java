package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.service.CarroService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        classes = CarrosApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest {

    @Autowired protected TestRestTemplate rest;

    @Autowired private CarroService service;

    private ResponseEntity<CarroDTO> getCarro(String url) {
        return rest.withBasicAuth("admin", "123").getForEntity(url, CarroDTO.class);
    }

    private ResponseEntity<List<CarroDTO>> getCarros(String url) {
        return rest.withBasicAuth("admin", "123")
                .exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarroDTO>>() {});
    }

    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity response =
                rest.withBasicAuth("admin", "123").postForEntity("/api/v1/carros", carro, null);
        System.out.println(response);

        // Verifica se criou
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();

        Assertions.assertNotNull(c);
        Assertions.assertEquals("Porshe", c.getNome());
        Assertions.assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        rest.withBasicAuth("admin", "123").delete(location);

        // Verificar se deletou
    }

    @Test
    public void testLista() {
        List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
        Assertions.assertNotNull(carros);
        // assertEquals(30, carros.size());

        ResponseEntity response =
                rest.withBasicAuth("admin", "123").postForEntity("/api/v1/carros", carros, null);
        System.out.println(response);
    }

    /* @Test
    public void testListaPorTipo() {

        assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
    }*/

    /*@Test
    public void testGetOk() {

        ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarroDTO c = response.getBody();
        assertEquals("Ferrari FF", c.getNome());
    }

    @Test
    public void testGetNotFound() {

        ResponseEntity response = getCarro("/api/v1/carros/40");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }*/
}
