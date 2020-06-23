package br.com.guilhermeevangelista.rest.core;

import br.com.guilhermeevangelista.rest.core.utils.PropertiesManager;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class BaseRequest {

    public static Scenario scenario;
    protected static Response response;
    protected static String token;

    protected static Map<String, Object> map;
    protected static Random random = new Random();

    protected static PropertiesManager propertiesManager = new PropertiesManager();

    public Response realizarGet(String api){
        response = given()
                .when()
                    .get("/"+api);

        addText("Fazendo um get na api '"+api+"' sem token");

        return response;
    }

    public Response realizarGet(String api, String token){
        response = given()
                    .header("Authorization", "JWT " + token)
                .when()
                    .get("/"+api);

        addText("Fazendo um get na api '"+api+"' com token");

        return response;
    }

    public Response realizarPost(String api, Map<String, Object> body){
        response = given()
                    .body(body)
                .when()
                    .post("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    public Response realizarPost(String api, Map<String, Object> body, String token){
        response = given()
                    .header("Authorization", "JWT " + token)
                    .body(body)
                .when()
                    .post("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    public Response realizarPost(String api, JSONObject body){
        response = given()
                    .body(body)
                .when()
                    .post("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    public Response realizarPost(String api, JSONObject body, String token){
        response = given()
                    .header("Authorization", "JWT " + token)
                    .body(body.toString())
//                    .log().all()
                .when()
                    .post("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    protected Response realizarPutComMap(String api, Map<String, Object> body) {
        response = given()
                    .body(body)
                .when()
                    .put("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    protected Response realizarPutComMap(String api, Map<String, Object> body, String token) {
        response = given()
                    .header("Authorization", "JWT " + token)
                    .body(body)
                .when()
                    .put("/"+api);

        addText("Body de envio:"+response.then().extract().body().asString());

        return response;
    }

    public String getToken(String user){
        map = new HashMap<>();

        map.put("email", propertiesManager.getProp("USER_"+user.toUpperCase()));
        map.put("senha", propertiesManager.getProp("PASSWORD_"+user.toUpperCase()));

        token = realizarPost("signin", map)
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

    public boolean bodyContains(Object text){
        return response.then()
                    .extract().body().asString().contains(String.valueOf(text));
    }

    protected void gerarLog(String texto) {

    }

    protected void gerarLogErro(String texto) {

    }

    public static void addText(String texto) {
        if (texto != null)
            scenario.write("\n"+texto);
    }

    protected String getBody() {
        return response.then().extract().body().asString();
    }
}
