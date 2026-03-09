package tests.resources;

import api.resources.ResourcesApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import tests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesPositiveTest extends BaseTest {

    private static ResourcesApi api;

    private static final String folderPath = "/autotest-folder-183";
    private static final String copiedFolder = "/autotest-folder-copy";
    private static final String movedFolder = "/autotest-folder-moved";

    @BeforeAll
    static void setup() {
        api = new ResourcesApi(baseUrl, token);
    }

    @Test
    @Order(1)
    void createFolderTest() {
        Response response = api.createFolder(folderPath);

        assertEquals(201, response.statusCode(), "Статус должен быть 201");
        assertNotNull(response.jsonPath().get("href"), "Ссылка на папку не должна быть null");
    }

    @Test
    @Order(2)
    void getFolderMetadataTest() {
        Response response = api.getResource(folderPath);

        assertEquals(200, response.statusCode(), "Статус должен быть 200");
        assertEquals("disk:" + folderPath, response.jsonPath().get("path"), "Путь папки не совпадает");
    }

    @Test
    @Order(3)
    void copyResourceTest() {
        Response response = api.copyResource(folderPath, copiedFolder, false);

        assertTrue(response.statusCode() == 201 || response.statusCode() == 202,
                "Статус должен быть 201 или 202");
    }

    @Test
    @Order(4)
    void moveResourceTest() {
        Response response = api.moveResource(copiedFolder, movedFolder);

        assertTrue(response.statusCode() == 201 || response.statusCode() == 202,
                "Статус должен быть 201 или 202");
    }

    @Test
    @Order(5)
    void deleteResourceTest() {
        Response response = api.deleteResource(folderPath);

        assertTrue(response.statusCode() == 202 || response.statusCode() == 204,
                "Статус должен быть 202 или 204");
    }
}