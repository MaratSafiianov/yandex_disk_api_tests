package api.operations;

import api.base.BaseApi;
import io.restassured.response.Response;

public class OperationsApi extends BaseApi {    // /v1/disk/operations/{id}

    private static final String BASE_PATH = "/v1/disk/operations";

    public OperationsApi(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // Получить статус операции
    public Response getOperationStatus(String operationId) {

        return get(
                BASE_PATH + "/" + operationId,
                null
        );
    }
}
