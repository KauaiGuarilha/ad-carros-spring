package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.domain.dto.exception.ObjectNotFoundException;
import com.carros.service.CarroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CarrosServiceTests {

	@Autowired CarroService service;

	@Test
	public void testSave(){

		Carro carro = new Carro(); //
		carro.setNome("Ferrari");
		carro.setTipo("Esportivo");

		CarroDTO c = service.insert(carro);

		Long id = c.getId();
		assertNotNull(c);

		// localizar o objeto
		c = service.getCarroById(id);
		assertNotNull(c);

		Assertions.assertEquals("Ferrari", c.getNome());
		Assertions.assertEquals("Esportivo", c.getTipo());

		//deletar
		service.delete(id);

		//verificar se deletou
		try {
			assertNull(service.getCarroById(id));
			fail("Não foi excluído");
		}catch (ObjectNotFoundException ex){
			//Ok
		}

	}


	@Test
	public void testLista() {

		List<CarroDTO> carros = service.getCarros();

		//assertEquals(36, carros.size());


	}

	/*@Test
	public void testListaPorTipo() {

		assertEquals(9, service.getCarroByTipo("classicos").size());
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());

		assertEquals(0, service.getCarroByTipo("x").size());
	}*/

	/*@Test
	public void testGet() {

		CarroDTO c = service.getCarroById(11L);

		assertNotNull(c);					//Rever
		assertEquals("Ferrari FF", c.getNome());
	}*/

}
