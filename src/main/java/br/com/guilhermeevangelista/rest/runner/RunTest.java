package br.com.guilhermeevangelista.rest.runner;

import br.com.guilhermeevangelista.rest.utils.Report;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( strict = true,
        plugin = {"html:target/cucumber-report", "json:target/cucumber.json"},
        features = {"src/resources/features"},
        glue = {"br/com/guilhermeevangelista/rest/steps", "br/com/guilhermeevangelista/rest/hooks"},
        tags = {"@incluirContaRepetida"})

public class RunTest {
    @AfterClass
    public static void gerarRelatorio(){
        Report.gerarRelatio();
    }
}
