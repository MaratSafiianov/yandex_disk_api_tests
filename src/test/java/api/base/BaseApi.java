package api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class BaseApi {

    protected String baseUrl;
    protected String token;

    public BaseApi(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    protected Response get(String path, Map<String, ?> params) {
        return RestAssured
                .given()
                .header("Authorization", "OAuth " + token)
                .queryParams(params)
                .get(baseUrl + path);
    }

    protected Response post(String path, Map<String, ?> params) {
        return RestAssured
                .given()
                .header("Authorization", "OAuth " + token)
                .queryParams(params)
                .post(baseUrl + path);
    }

    protected Response put(String path, Map<String, ?> params) {
        return RestAssured
                .given()
                .header("Authorization", "OAuth " + token)
                .queryParams(params)
                .put(baseUrl + path);
    }

    protected Response patch(String path, Map<String, ?> params, Object body) {
        return RestAssured
                .given()
                .header("Authorization", "OAuth " + token)
                .queryParams(params)
                .body(body)
                .patch(baseUrl + path);
    }

    protected Response delete(String path, Map<String, ?> params) {
        return RestAssured
                .given()
                .header("Authorization", "OAuth " + token)
                .queryParams(params)
                .delete(baseUrl + path);
    }
}
