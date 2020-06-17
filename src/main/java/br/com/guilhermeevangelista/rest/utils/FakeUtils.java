package br.com.guilhermeevangelista.rest.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakeUtils {

    private final Faker faker = new Faker(new Locale("pt-br"));

    public String getNomeCompania(){
        return faker.company().name();
    }

}
