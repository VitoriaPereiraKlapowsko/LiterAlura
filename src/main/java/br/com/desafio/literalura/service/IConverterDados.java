package br.com.desafio.literalura.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}