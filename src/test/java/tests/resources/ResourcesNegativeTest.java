package tests.resources;

import api.resources.ResourcesApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import tests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesNegativeTest extends BaseTest {

    private static ResourcesApi api;

    @BeforeAll
    static void setup() {
        api = new ResourcesApi(baseUrl, token);
    }

    @Test
    @Order(1)
    void deleteNonExistingResourceTest() {
        Response response = api.deleteResource("/non-existing-folder");
        assertEquals(404, response.statusCode(), "Удаление несуществующей папки должно вернуть 404");
    }

    @Test
    @Order(2)
    void createExistingFolderTest() {
        String path = "/existing-folder";

        // Создаём папку первый раз
        api.createFolder(path);

        // Попытка создать снова
        Response response = api.createFolder(path);
        assertEquals(409, response.statusCode(), "Создание уже существующей папки должно вернуть 409");
    }

    @Test
    @Order(3)
    void requestWithoutTokenTest() {
        Response response = RestAssured
                .given()
                .queryParam("path", "/test-folder")
                .get(baseUrl + "/v1/disk/resources");

        assertEquals(401, response.statusCode(), "Запрос без токена должен вернуть 401");
    }

    @Test
    @Order(4)
    void invalidPathTest() {
        Response response = api.getResource("???invalid-path");
        assertEquals(404, response.statusCode(), "Некорректный путь должен вернуть 404");
    }
}