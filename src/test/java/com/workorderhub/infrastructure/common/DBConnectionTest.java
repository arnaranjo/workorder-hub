package com.workorderhub.infrastructure.common;

import com.workorderhub.infrastructure.common.DBConnection;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Ciclo de Vida de Conexión")
public class DBConnectionTest {
    @Test
    @DisplayName("Prueba de Conexión")
    void testDBConnect() {

        Connection connection = DBConnection.DBConnect();
        assertNotNull(connection, "La conexión no debería ser nula. Revisa el archivo .properties y el driver.");

        try {
            assertTrue(connection.isValid(2), "La conexión debería ser válida y responder en menos de 2 segundos.");

        } catch (SQLException e) {
            fail("Error al validar la conexión: " + e.getMessage());

        }
    }

    @Test
    @DisplayName("Prueba de Desconexión")
    void testDBDisconnect() {

        Connection connection = DBConnection.DBConnect();
        assertNotNull(connection);

        DBConnection.DBDisconnect();

        try {

            assertTrue(connection.isClosed(), "La conexión debería estar marcada como cerrada después de llamar a DBDisconnect.");
        } catch (SQLException e) {
            fail("Error al verificar el estado de cierre: " + e.getMessage());

        }
    }
}
