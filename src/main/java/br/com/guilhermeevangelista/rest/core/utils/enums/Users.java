package br.com.guilhermeevangelista.rest.core.utils.enums;

public enum Users {
    PADRAO("RestGuilherme@TestAutomacao1", "testsenha1");

    String email, senha;

    Users(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

}
