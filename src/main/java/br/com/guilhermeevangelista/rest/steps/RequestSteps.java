package br.com.guilhermeevangelista.rest.steps;

import br.com.guilhermeevangelista.rest.utils.PropertiesManager;
import br.com.guilhermeevangelista.rest.utils.FakeUtils;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class RequestSteps{

    protected static Response response;
    protected static String token;

    protected static Map<String, Object> map;
    protected static Random random = new Random();

    protected static PropertiesManager propertiesManager = new PropertiesManager();

    protected FakeUtils fakeUtils = new FakeUtils();

    public Response realizarGet(String api){
        response = given()
                .when()
                    .get("/"+api);

        return response;
    }

    public Response realizarGet(String api, String token){
        response = given()
                    .header("Authorization", "JWT " + token)
                .when()
                    .get("/"+api);

        return response;
    }

    public Response realizarPostComMap(String api, Map<String, Object> body){
        response = given()
                    .body(body)
                .when()
                    .post("/"+api);

        return response;
    }

    public Response realizarPostComMap(String api, Map<String, Object> body, String token){
        response = given()
                    .header("Authorization", "JWT " + token)
                    .body(body)
                .when()
                    .post("/"+api);

        return response;
    }

    protected Response realizarPutComMap(String api, Map<String, Object> body) {
        response = given()
                    .body(body)
                .when()
                    .put("/"+api);

        return response;
    }

    protected Response realizarPutComMap(String api, Map<String, Object> body, String token) {
        response = given()
                    .header("Authorization", "JWT " + token)
                    .body(body)
                .when()
                    .put("/"+api);

        return response;
    }

    public String getToken(String user){
        map = new HashMap<>();

        map.put("email", propertiesManager.getProp("USER_"+user.toUpperCase()));
        map.put("senha", propertiesManager.getProp("PASSWORD_"+user.toUpperCase()));

        token = realizarPostComMap("signin", map)
                .then()
//                    .log().all()
                    .extract().path("token");

        assert  getStatusCode() == 200;

        return token;
    }

    public ArrayList<Object> getListaPorPath(String path){
        return response.then()
                .extract().path(path);
    }

    public int getStatusCode(){
        return response.then()
                .extract().statusCode();
    }

    public boolean bodyContains(String text){
        return response.then()
                    .extract().body().asString().contains(text);
    }

    protected void gerarLog(String texto) {

    }

    protected void gerarLogErro(String texto) {

    }
}
