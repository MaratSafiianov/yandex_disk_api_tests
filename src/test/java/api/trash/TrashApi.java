package api.trash;

import api.base.BaseApi;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TrashApi extends BaseApi {     // /v1/disk/trash/resources

    private static final String BASE_PATH = "/v1/disk/trash/resources";

    public TrashApi(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // Получить содержимое корзины
    public Response getTrash() {

        return get(BASE_PATH, Map.of(
                "path", "/"
        ));
    }

    // Очистить корзину полностью
    public Response clearTrash() {

        return delete(BASE_PATH, new HashMap<>());
    }

    // Удалить конкретный ресурс из корзины
    public Response deleteFromTrash(String path) {

        return delete(BASE_PATH, Map.of(
                "path", path
        ));
    }

    // Восстановить ресурс
    public Response restore(String path) {

        return put(
                BASE_PATH + "/restore",
                Map.of("path", path)
        );
    }

    // Восстановить с параметрами
    public Response restore(String path, String name, boolean overwrite) {

        Map<String, Object> params = new HashMap<>();

        params.put("path", path);
        params.put("name", name);
        params.put("overwrite", overwrite);

        return put(
                BASE_PATH + "/restore",
                params
        );
    }
}
