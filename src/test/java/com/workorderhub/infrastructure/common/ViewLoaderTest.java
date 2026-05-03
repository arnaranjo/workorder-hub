package com.workorderhub.infrastructure.common;

import javafx.util.Callback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViewLoaderTest {
    private ViewLoader viewLoader;

    @BeforeEach
    void setUp() {
        viewLoader = new ViewLoader();
    }

    @Test
    @DisplayName("Debe retornar la instancia registrada en el Supplier")
    void shouldReturnRegisteredController() {

        FakeController instanciaEsperada = new FakeController();
        viewLoader.registerController(FakeController.class, () -> instanciaEsperada);

        Callback<Class<?>, Object> factory = viewLoader.buildControllerFactory();

        // Request a controller witch is registered in the factory
        Object resultado = factory.call(FakeController.class);
        assertSame(instanciaEsperada, resultado, "La factoría debe devolver la instancia del Supplier");
    }

    @Test
    @DisplayName("Debe crear una nueva instancia por defecto si no está registrado")
    void shouldCreateNewInstanceIfNotRegistered() {

        // Request a controller which is not registered in the factory
        Callback<Class<?>, Object> factory = viewLoader.buildControllerFactory();
        Object resultado = factory.call(FakeController.class);

        assertNotNull(resultado);
        assertInstanceOf(FakeController.class, resultado);
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException si el controlador no tiene constructor vacío")
    void shouldThrowExceptionOnInvalidController() {
        Callback<Class<?>, Object> factory = viewLoader.buildControllerFactory();

        assertThrows(RuntimeException.class, () -> {
            factory.call(ControllerInvalido.class);
        }, "Debería fallar porque la clase no se puede instanciar por defecto");
    }


    // Clases auxiliary para las pruebas

    public static class FakeController {
        public FakeController() {}
    }

    public static class ControllerInvalido {
        private ControllerInvalido(String texto) {}
    }
}
