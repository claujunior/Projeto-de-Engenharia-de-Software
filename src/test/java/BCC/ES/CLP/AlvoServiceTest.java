package BCC.ES.CLP;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.repository.RepositoryAlvo;
import BCC.ES.CLP.service.ServiceAlvo;


public class AlvoServiceTest {

    @Mock
    private RepositoryAlvo repositoryAlvo;

    @InjectMocks
    private ServiceAlvo service;

    public AlvoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaDeAlvos() {
        
        List<Alvo> listaFake = List.of(new Alvo(), new Alvo());

        when(repositoryAlvo.findAll()).thenReturn(listaFake);

       
        List<Alvo> resultado = service.allAlvos();

      
        assertEquals(2, resultado.size());
        verify(repositoryAlvo).findAll();
    }
}