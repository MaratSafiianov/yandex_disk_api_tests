package api.resources;

import api.base.BaseApi;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ResourcesApi extends BaseApi { // /v1/disk/resources

    private static final String BASE_PATH = "/v1/disk/resources";

    public ResourcesApi(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // Создать папку
    public Response createFolder(String path) {
        return put(BASE_PATH, Map.of("path", path));
    }

    // Получить метаинформацию о файле или папке
    public Response getResource(String path) {
        return get(BASE_PATH, Map.of("path", path));
    }

    // Получить метаинформацию с параметрами
    public Response getResource(String path, int limit, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("limit", limit);
        params.put("offset", offset);

        return get(BASE_PATH, params);
    }

    // Удалить ресурс
    public Response deleteResource(String path) {
        return delete(BASE_PATH, Map.of("path", path));
    }

    // Удалить ресурс навсегда
    public Response deleteResourcePermanently(String path) {
        return delete(BASE_PATH, Map.of(
                "path", path,
                "permanently", true
        ));
    }

    // Удалить асинхронно
    public Response deleteAsync(String path) {
        return delete(BASE_PATH, Map.of(
                "path", path,
                "force_async", true
        ));
    }

    // Копировать файл или папку
    public Response copyResource(String from, String path, boolean overwrite) {

        Map<String, Object> params = new HashMap<>();
        params.put("from", from);
        params.put("path", path);
        params.put("overwrite", overwrite);

        return post("/v1/disk/resources/copy", params);
    }

    // Переместить файл
    public Response moveResource(String from, String path) {

        Map<String, Object> params = new HashMap<>();
        params.put("from", from);
        params.put("path", path);

        return post("/v1/disk/resources/move", params);
    }

    // Получить ссылку для скачивания
    public Response getDownloadLink(String path) {
        return get("/v1/disk/resources/download", Map.of("path", path));
    }

    // Получить ссылку для загрузки
    public Response getUploadLink(String path, boolean overwrite) {

        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("overwrite", overwrite);

        return get("/v1/disk/resources/upload", params);
    }

    // Загрузить файл по URL
    public Response uploadFromUrl(String path, String url) {

        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("url", url);

        return post("/v1/disk/resources/upload", params);
    }

    // Получить список файлов
    public Response getFiles(int limit) {
        return get("/v1/disk/resources/files", Map.of("limit", limit));
    }

    // Получить последние загруженные файлы
    public Response getLastUploaded(int limit) {
        return get("/v1/disk/resources/last-uploaded", Map.of("limit", limit));
    }

    // Опубликовать файл
    public Response publishResource(String path) {
        return put("/v1/disk/resources/publish", Map.of("path", path));
    }

    // Снять публикацию
    public Response unpublishResource(String path) {
        return put("/v1/disk/resources/unpublish", Map.of("path", path));
    }

    // Получить short info
    public Response getShortInfo(String path) {
        return get("/v1/disk/resources/short-info", Map.of("path", path));
    }
}
