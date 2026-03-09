package tests.trash;

import api.resources.ResourcesApi;
import api.trash.TrashApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import tests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrashTest extends BaseTest {

    private static ResourcesApi resourcesApi;
    private static TrashApi trashApi;

    private static final String folder = "/trash_test_folder-1";

    @BeforeAll
    static void setup() {
        resourcesApi = new ResourcesApi(baseUrl, token);
        trashApi = new TrashApi(baseUrl, token);

        // создаем папку
        Response createResponse = resourcesApi.createFolder(folder);
        assertTrue(createResponse.statusCode() == 201 || createResponse.statusCode() == 409);

        // удаляем папку в корзину
        Response deleteResponse = resourcesApi.deleteResource(folder);
        assertTrue(deleteResponse.statusCode() == 202 || deleteResponse.statusCode() == 204);
    }

    @Test
    @Order(1)
    void shouldGetTrashContent() {
        Response response = trashApi.getTrash();

        assertEquals(200, response.statusCode(), "Статус должен быть 200");

        Integer total = response.jsonPath().getInt("_embedded.total");
        assertTrue(total >= 0, "Общее количество элементов в корзине должно быть >= 0");
    }

    @Test
    @Order(2)
    void shouldRestoreResource() {
        Response response = trashApi.restore(folder);

        assertTrue(response.statusCode() == 201 || response.statusCode() == 202,
                "Статус должен быть 201 или 202, но получен: " + response.statusCode());
    }

    @Test
    @Order(3)
    void shouldDeleteFromTrash() {
        // снова удалим
        Response deleteResponse = resourcesApi.deleteResource(folder);
        assertTrue(deleteResponse.statusCode() == 202 || deleteResponse.statusCode() == 204);

        Response response = trashApi.deleteFromTrash(folder);
        assertTrue(response.statusCode() == 202 || response.statusCode() == 204,
                "Статус должен быть 202 или 204, но получен: " + response.statusCode());
    }

    @Test
    @Order(4)
    void shouldClearTrash() {
        Response response = trashApi.clearTrash();

        assertTrue(response.statusCode() == 202 || response.statusCode() == 204,
                "Статус должен быть 202 или 204");
    }
}