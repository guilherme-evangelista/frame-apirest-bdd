package br.com.guilhermeevangelista.rest.steps;

import br.com.guilhermeevangelista.rest.core.BaseRequest;
import br.com.guilhermeevangelista.rest.core.utils.FakeUtils;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

public class ContasSteps extends BaseRequest {

    private static String nomeCompania;
    private List<Object> contasList;

    @Quando("faco uma requisicao GET na api de contas {string} token")
    public void facoUmaRequisicaoGETNaApiDeContasToken(String comOuSem) {
        switch (comOuSem){
            default:
                throw new IllegalArgumentException();
            case "com":
                super.realizarGet("contas", token);
                break;
            case "sem":
                super.realizarGet("contas");
                break;
        }
    }

    @Quando("faco uma requisicao POST na api de contas {string} token")
    public void facoUmaRequisicaoPOSTNaApiDeContasToken(String comOuSem) {
        map = new HashMap<>();
        nomeCompania = FakeUtils.gerarNomeCompania();
        map.put("nome", nomeCompania);

        switch (comOuSem){
            default:
                throw new IllegalArgumentException();
            case "com":
                super.realizarPost("contas", map, token);
                break;
            case "sem":
                super.realizarPost("contas", map);
                break;
        }

    }

    @Quando("faco uma requisicao POST na api de contas {string} token e com nome repetido")
    public void facoUmaRequisicaoPOSTNaApiDeContasTokenComNomeRepetido(String comOuSem) {
        this.facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("nome");

        nomeCompania = String.valueOf(contasList.get(random.nextInt(contasList.size())));
        map = new HashMap<>();
        map.put("nome", nomeCompania);

        switch (comOuSem){
            default:
                throw new IllegalArgumentException();
            case "com":
                super.realizarPost("contas", map, token);
                break;
            case "sem":
                super.realizarPost("contas", map);
                break;
        }
    }

    @Quando("faco uma requisicao PUT na api de contas {string} token")
    public void facoUmaRequisicaoPUTNaApiDeContasToken(String comOuSem) {
        this.facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("id");

        nomeCompania = FakeUtils.gerarNomeCompania();
        map = new HashMap<>();
        map.put("nome", nomeCompania);

        String id = String.valueOf(contasList.get(random.nextInt(contasList.size())));

        switch (comOuSem){
            default:
                throw new IllegalArgumentException();
            case "com":
                super.realizarPutComMap("contas/"+id, map, token);
                break;
            case "sem":
                super.realizarPutComMap("contas", map);
                break;
        }
    }

    @Entao("valido o status code {int}")
    public void validoOStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, super.getStatusCode());
        addText("Status code retornado: " + super.getStatusCode());
    }

    @E("valido a mensagem de usuario nao autorizado")
    public void validoAMensagemDeUsuarioNaoAutorizado() {
        Assert.assertTrue(super.bodyContains("Unauthorized"));
        addText("Body de retorno: " + super.getBody());
    }

    @E("valido que o nome da conta foi inserido de acordo")
    public void queONomeDaContaFoiInseridoDeAcordo() {
        Assert.assertTrue(super.bodyContains(nomeCompania));
        addText("Body de retorno: " + super.getBody());
    }

    @E("valido que o nome da conta foi alterado de acordo")
    public void validoQueONomeDaContaFoiAlteradoDeAcordo() {
        this.queONomeDaContaFoiInseridoDeAcordo();
        addText("Body de retorno: " + super.getBody());
    }

    @E("valido a mensagem {string}")
    public void validoAMensagem(String mensagem) {
        Assert.assertTrue(super.bodyContains(mensagem));
        addText("Body de retorno: " + super.getBody());
    }

    @E("certifique que eu tenho uma conta criada")
    public void certifiqueQueEuTenhoUmaContaCriada() {
        facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("id");
        Assert.assertTrue(contasList.size()>0);
        addText("Quantidade de contas recuperadas: " + contasList.size());
    }
}
