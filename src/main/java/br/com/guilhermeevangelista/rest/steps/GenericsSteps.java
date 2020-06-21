package br.com.guilhermeevangelista.rest.steps;

import br.com.guilhermeevangelista.rest.core.RequestSteps;
import io.cucumber.java.pt.Dado;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class GenericsSteps extends RequestSteps {
    @Dado("que eu obtenha o token previamente do user {string}")
    public void queEuObtenhaOTokenPreviamenteDoUser(String conta) {
        Assert.assertNotNull(super.getToken(conta));
    }
}
