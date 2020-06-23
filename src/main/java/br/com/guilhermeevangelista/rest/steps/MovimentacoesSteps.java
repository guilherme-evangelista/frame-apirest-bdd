package br.com.guilhermeevangelista.rest.steps;

import br.com.guilhermeevangelista.rest.core.BaseRequest;
import br.com.guilhermeevangelista.rest.core.utils.FakeUtils;
import br.com.guilhermeevangelista.rest.core.utils.JsonUtils;

import br.com.guilhermeevangelista.rest.core.utils.date.DateFormatUtils;
import br.com.guilhermeevangelista.rest.core.utils.date.DateGetUtils;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovimentacoesSteps extends BaseRequest {
    ContasSteps contasSteps = new ContasSteps();
    List<Object> contasList;

    @Quando("faco uma requisicao Post na api de transacoes {string} token")
    public void facoUmaRequisicaoPostNaApiDeTransacoesToken(String comOuSem) throws IOException {
        switch (comOuSem){
            default:
                throw new IllegalArgumentException("Valor passado na feature 'com ou sem', invalido!!");
            case "com":
                super.realizarPost("transacoes", gerarValoresDeMovimentacao(), token);
                break;
            case "sem":
                super.realizarPost("transacoes", gerarValoresDeMovimentacao());
                break;
        }
    }

    @Então("valido que a movimentacao foi inserida com sucesso")
    public void validoQueAMovimentacaoFoiInseridaComSucesso() {
        Assert.assertEquals(201, super.getStatusCode());
        addText("Status code retornado: "+response.then().extract().statusCode());
    }

    public JSONObject gerarValoresDeMovimentacao() throws IOException {
        return JsonUtils.obterArquivoJson("Movimentacoes", getMapRandomMovimentacao());
    }

    private Map<String, Object> getMapRandomMovimentacao(){
        contasSteps.facoUmaRequisicaoGETNaApiDeContasToken("com");
        contasList = super.getListaPorPath("id");

        map = new HashMap<>();
        map.put("conta_id", contasList.get(random.nextInt(contasList.size())));
        map.put("descricao", "Boleto da "+FakeUtils.gerarNomeCompania());
        map.put("envolvido", FakeUtils.gerarNomeCompleto());
        map.put("tipo", random.nextInt(2)==0? "DESP" : "REC");
        map.put("data_transacao", DateGetUtils.getDate(true, DateFormatUtils.DD_MM_YYYY_BARRA));
        map.put("data_pagamento", DateGetUtils.getDate(DateFormatUtils.DD_MM_YYYY_BARRA));
        map.put("valor", FakeUtils.gerarValorTransacaoAleatorio());
        map.put("status", random.nextInt(2) == 0);
        return map;
    }
}

