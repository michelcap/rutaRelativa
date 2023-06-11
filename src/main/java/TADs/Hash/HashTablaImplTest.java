package TADs.Hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTablaImplTest {

    @Test
    void put() throws Exception {
        HashTablaImpl<String, String> tabla = new HashTablaImpl<>(10);
        tabla.put("Pedro", "Pedro_0");
        tabla.put("Roberto", "roberto_1");
        tabla.put("Maria", "Maria_2");
        tabla.put("Juan", "Juan_3");
        tabla.put("Pancracio", "Pancracio_4");
        tabla.put("Juan Pedro", "Juan Pedro_5");
        assertEquals(6, tabla.getSize());
    }

    @Test
    void contains() throws Exception {
        HashTablaImpl<String, String> tabla = new HashTablaImpl<>(10);
        tabla.put("Pedro", "Pedro_0");
        tabla.put("Roberto", "roberto_1");
        tabla.put("Maria", "Maria_2");
        tabla.put("Juan", "Juan_3");
        tabla.put("Pancracio", "Pancracio_4");
        tabla.put("Juan Pedro", "Juan Pedro_5");
        assertTrue(tabla.contains("Juan Pedro"));
    }

    @Test
    void remove() throws Exception {
        HashTablaImpl<String, String> tabla = new HashTablaImpl<>(10);
        tabla.put("Pedro", "Pedro_0");
        tabla.put("Roberto", "roberto_1");
        tabla.put("Maria", "Maria_2");
        tabla.put("Juan", "Juan_3");
        tabla.put("Pancracio", "Pancracio_4");
        tabla.put("Juan Pedro", "Juan Pedro_5");
        tabla.remove("Juan Pedro");
        assertFalse(tabla.contains("Juan Pedro"));
    }

    @Test
    void get() throws Exception {
        HashTablaImpl<String, String> tabla = new HashTablaImpl<>(10);
        tabla.put("Pedro", "Pedro_0");
        tabla.put("Roberto", "roberto_1");
        tabla.put("Maria", "Maria_2");
        tabla.put("Juan", "Juan_3");
        tabla.put("Pancracio", "Pancracio_4");
        tabla.put("Juan Pedro", "Juan Pedro_5");
        assertEquals("Juan Pedro_5", tabla.get("Juan Pedro").getData());
    }
}