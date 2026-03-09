package api.publicresources;

import api.base.BaseApi;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PublicResourcesApi extends BaseApi {   // /v1/disk/public/resources

    private static final String BASE_PATH = "/v1/disk/public/resources";

    public PublicResourcesApi(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // Получить метаинформацию о публичном ресурсе
    public Response getPublicResource(String publicKey) {

        return get(BASE_PATH, Map.of(
                "public_key", publicKey
        ));
    }

    // Получить метаинформацию с параметрами
    public Response getPublicResource(String publicKey, String path, int limit, int offset) {

        Map<String, Object> params = new HashMap<>();

        params.put("public_key", publicKey);
        params.put("path", path);
        params.put("limit", limit);
        params.put("offset", offset);

        return get(BASE_PATH, params);
    }

    // Получить ссылку для скачивания публичного ресурса
    public Response getDownloadLink(String publicKey) {

        return get(BASE_PATH + "/download",
                Map.of("public_key", publicKey));
    }

    // Получить ссылку для скачивания файла внутри публичной папки
    public Response getDownloadLink(String publicKey, String path) {

        Map<String, Object> params = new HashMap<>();
        params.put("public_key", publicKey);
        params.put("path", path);

        return get(BASE_PATH + "/download", params);
    }

    // Сохранить публичный ресурс на свой диск
    public Response saveToDisk(String publicKey) {

        return post(BASE_PATH + "/save-to-disk",
                Map.of("public_key", publicKey));
    }

    // Сохранить публичный ресурс с параметрами
    public Response saveToDisk(String publicKey, String name, String savePath, boolean async) {

        Map<String, Object> params = new HashMap<>();

        params.put("public_key", publicKey);
        params.put("name", name);
        params.put("save_path", savePath);
        params.put("force_async", async);

        return post(BASE_PATH + "/save-to-disk", params);
    }

    // Обновить настройки публичной ссылки
    public Response updatePublicSettings(String path, Object body) {

        return patch(
                BASE_PATH + "/public-settings",
                Map.of("path", path),
                body
        );
    }
}
