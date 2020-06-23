package br.com.guilhermeevangelista.rest.core.hooks;

import br.com.guilhermeevangelista.rest.core.BaseRequest;
import br.com.guilhermeevangelista.rest.core.utils.PropertiesManager;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

public class Hooks {

    @Before
    public void setup(Scenario scenario){
        PropertiesManager manager = new PropertiesManager();

        RestAssured.baseURI = manager.getProp("APP_BASE_URI");
        RestAssured.port = Integer.parseInt(manager.getProp("APP_PORT"));
        RestAssured.basePath = manager.getProp("APP_BASE_PATH");

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON)
//                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().expectResponseTime(Matchers.lessThan(Long.parseLong(manager.getProp("MAX_TIMEOUT"))))
//                .log(LogDetail.ALL)
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        BaseRequest.scenario = scenario;
    }

}
