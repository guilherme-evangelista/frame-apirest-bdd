package br.com.guilhermeevangelista.rest.runner;

import br.com.guilhermeevangelista.rest.core.utils.report.Report;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true,
        plugin = {"pretty", "json:target/cucumber-report/cucumber.json"},
        features = {"src/main/resources/features"},
        glue = {"br/com/guilhermeevangelista/rest/steps", "br/com/guilhermeevangelista/rest/core/hooks"},
        tags = {"@regression"})

public class RunTest {
    @AfterClass
    public static void gerarRelatorio() throws IOException {
        Report.gerarRelarotioClueCumber();
    }
}