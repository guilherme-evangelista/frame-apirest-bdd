package br.com.guilhermeevangelista.rest.runner;

import br.com.guilhermeevangelista.rest.core.utils.Report;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( strict = true,
        plugin = {"html:target/cucumber-report", "json:target/cucumber.json"},
        features = {"src/main/resources/features"},
        glue = {"br/com/guilhermeevangelista/rest/steps", "br/com/guilhermeevangelista/rest/core/hooks"},
        tags = {"@movimentacao"})

public class RunTest {
    @AfterClass
    public static void gerarRelatorio(){
            Report.gerarRelatio();

    }
}
