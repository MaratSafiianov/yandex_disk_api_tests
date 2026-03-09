package tests.publicresources;

import api.publicresources.PublicResourcesApi;
import api.resources.ResourcesApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import tests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PublicResourcesTest extends BaseTest {

    private static PublicResourcesApi publicApi;
    private static ResourcesApi resourcesApi;

    // Имя временной папки для тестов
    private final String folderPath = "/autotest-public-folder-184";
    private String publicKey;

    @BeforeAll
    static void setup() {
        publicApi = new PublicResourcesApi(baseUrl, token);
        resourcesApi = new ResourcesApi(baseUrl, token);
    }

    @BeforeEach
    public void createAndPublishFolder() {
        // Создать папку (201 = создано, 409 = уже существует)
        Response createResponse = resourcesApi.createFolder(folderPath);
        assertTrue(
                createResponse.statusCode() == 201 ||
                        createResponse.statusCode() == 409,
                "Создание папки не прошло"
        );

        // Опубликовать папку
        Response publishResponse = resourcesApi.publishResource(folderPath);
        assertEquals(200, publishResponse.statusCode(), "Публикация папки не удалась");

        // Получить метаинформацию и publicKey
        Response metadata = resourcesApi.getResource(folderPath);
        publicKey = metadata.jsonPath().getString("public_key");
        assertNotNull(publicKey, "publicKey не получен");
    }


    @Test
    @Order(1)
    void shouldGetPublicResourceMetadata() {
        Response response = publicApi.getPublicResource(publicKey);
        assertEquals(200, response.statusCode(), "Метаданные публичного ресурса не получены");
        assertNotNull(response.jsonPath().get("name"), "Имя ресурса пустое");
    }

    @Test
    @Order(2)
    void shouldGetDownloadLink() {
        Response response = publicApi.getDownloadLink(publicKey);
        assertEquals(200, response.statusCode(), "Ссылка на скачивание не получена");
        assertNotNull(response.jsonPath().get("href"), "Href пустой");
    }

    @Test
    @Order(3)
    void shouldSavePublicResourceToDisk() {
        Response response = publicApi.saveToDisk(publicKey);
        assertTrue(
                response.statusCode() == 201 || response.statusCode() == 202,
                "Сохранение ресурса на диск не прошло"
        );
    }
}
