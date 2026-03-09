package api.disk;

import api.base.BaseApi;
import io.restassured.response.Response;

import java.util.Map;

public class DiskApi extends BaseApi {  // /v1/disk

    public DiskApi(String baseUrl, String token) {
        super(baseUrl, token);
    }

    public Response getDiskInfo() {
        return get("/v1/disk/", Map.of());
    }
}
