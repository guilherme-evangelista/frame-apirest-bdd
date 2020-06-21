package br.com.guilhermeevangelista.rest.core.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;


public class JsonUtils {

    private static String value;

    public static JSONObject atualizarCampoJson(JSONObject json, String chave, Object novoValor){

        Iterator<String> iterator = json.keys();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();

            if ((json.optJSONArray(key) == null) && (json.optJSONObject(key) == null)) {
                if ((key.equals(chave))) {
                    json.put(key, novoValor);
                    return json;
                }
            }

            if (json.optJSONObject(key) != null) {
                atualizarCampoJson(json.getJSONObject(key), chave, novoValor);
            }

            if (json.optJSONArray(key) != null) {
                JSONArray jArray = json.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    atualizarCampoJson(jArray.getJSONObject(i), chave, novoValor);
                }

            }
        }
        return json;
    }

    public static JSONObject obterArquivoJson(String nomeArquivo) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(nomeArquivo+".json")));
        return new JSONObject(content);
    }

    public static JSONObject obterArquivoJson(String nomeArquivo, Map<String, Object> mapChavesEValores) throws IOException, JSONException {
        JSONObject json = obterArquivoJson("src/main/resources/jsonTemplates/" + nomeArquivo);
        JSONObject newJson = null;
        for (Map.Entry<String, Object> entry : mapChavesEValores.entrySet()) {
            String chave = entry.getKey();
            Object valor = entry.getValue();
            try {
                newJson = atualizarCampoJson(json, chave, valor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newJson;
    }

    public static String obterValorPorChave(JSONObject json, String chave) throws JSONException {
        Iterator<String> iterator = json.keys();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();

            if ((json.optJSONArray(key) == null) && (json.optJSONObject(key) == null)) {
                if ((key.equals(chave))) {
                    value = json.get(key).toString();
                }
            }
            if (json.optJSONObject(key) != null) {
                obterValorPorChave(json.getJSONObject(key), chave);
            }
            if (json.optJSONArray(key) != null) {
                JSONArray jArray = json.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    obterValorPorChave(jArray.getJSONObject(i), chave);
                }
            }
        }
        return value;
    }

    public static JSONObject removerValoresPorChave(JSONObject json, String chave) throws JSONException {
        Iterator<String> iterator = json.keys();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();

            if ((json.optJSONArray(key) == null) && (json.optJSONObject(key) == null)) {
                if ((key.equals(chave))) {
                    json.remove(key);
                    return json;
                }
            }
            if (json.optJSONObject(key) != null) {
                removerValoresPorChave(json.getJSONObject(key), chave);
            }
            if (json.optJSONArray(key) != null) {
                JSONArray jArray = json.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    removerValoresPorChave(jArray.getJSONObject(i), chave);
                }
            }
        }
        return json;
    }
}
