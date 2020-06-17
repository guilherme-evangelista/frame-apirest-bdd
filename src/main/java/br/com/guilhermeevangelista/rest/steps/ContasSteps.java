package br.com.guilhermeevangelista.rest.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.*;

public class ContasSteps extends RequestSteps {

    private static String nomeCompania;
    private List<Object> contasList;

    @Dado("que eu obtenha o token previamente do user {string}")
    public void queEuObtenhaOTokenPreviamenteDoUser(String conta) {
        Assert.assertThat(super.getToken(conta), Matchers.notNullValue());
    }

    @Quando("faco uma requisicao GET na api de contas {string} token")
    public void facoUmaRequisicaoGETNaApiDeContasToken(String comOuSem) {
        if (comOuSem.equalsIgnoreCase("com"))
            super.realizarGet("contas", token);
        else if (comOuSem.equalsIgnoreCase("sem"))
            super.realizarGet("contas");
        else
            super.gerarLogErro("Argumento Invalido");
    }

    @Quando("faco uma requisicao POST na api de contas {string} token")
    public void facoUmaRequisicaoPOSTNaApiDeContasToken(String comOuSem) {

        nomeCompania = super.fakeUtils.getNomeCompania();

        map = new HashMap<>();
        map.put("nome", nomeCompania);

        if (comOuSem.equalsIgnoreCase("com"))
            super.realizarPostComMap("contas", map, token);
        else if (comOuSem.equalsIgnoreCase("sem"))
            super.realizarPostComMap("contas", map);
        else
            super.gerarLogErro("Argumento Invalido");
    }

    @Quando("faco uma requisicao POST na api de contas {string} token e com nome repetido")
    public void facoUmaRequisicaoPOSTNaApiDeContasTokenComNomeRepetido(String comOuSem) {
        this.facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("nome");

        nomeCompania = String.valueOf(contasList.get(random.nextInt(contasList.size())));

        map = new HashMap<>();
        map.put("nome", nomeCompania);

        if (comOuSem.equalsIgnoreCase("com"))
            super.realizarPostComMap("contas", map, token);
        else if (comOuSem.equalsIgnoreCase("sem"))
            super.realizarPostComMap("contas", map);
        else
            super.gerarLogErro("Argumento Invalido");

    }

    @Quando("faco uma requisicao PUT na api de contas {string} token")
    public void facoUmaRequisicaoPUTNaApiDeContasToken(String comOuSem) {

        this.facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("id");

        nomeCompania = super.fakeUtils.getNomeCompania();

        map = new HashMap<>();
        map.put("nome", nomeCompania);

        String id = String.valueOf(contasList.get(random.nextInt(contasList.size())));

        if (comOuSem.equalsIgnoreCase("com"))
            super.realizarPutComMap("contas/"+id, map, token);
        else if (comOuSem.equalsIgnoreCase("sem"))
            super.realizarPutComMap("contas", map);
        else
            super.gerarLogErro("Argumento Invalido");
    }

    @Entao("valido o status code {int}")
    public void validoOStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, super.getStatusCode());
    }

    @E("valido a mensagem de usuario nao autorizado")
    public void validoAMensagemDeUsuarioNaoAutorizado() {
        Assert.assertTrue(super.bodyContains("Unauthorized"));
    }

    @E("valido que o nome da conta foi inserido de acordo")
    public void queONomeDaContaFoiInseridoDeAcordo() {
        Assert.assertTrue(super.bodyContains(nomeCompania));
    }

    @E("valido que o nome da conta foi alterado de acordo")
    public void validoQueONomeDaContaFoiAlteradoDeAcordo() {
        this.queONomeDaContaFoiInseridoDeAcordo();
    }

    @E("valido a mensagem {string}")
    public void validoAMensagem(String mensagem) {
        Assert.assertTrue(super.bodyContains(mensagem));
    }
}
