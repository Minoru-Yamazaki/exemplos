package br.com.exemplos.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final String code;

    private final int statusCode;

    public DomainException(String code, String message, int statusCode) {
        super(message);
        this.code = code;
        this.statusCode = statusCode;
    }

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
        this.statusCode = 400;
    }

    public String getCode() {
        return code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static DomainException of(String code, String message) {
        return new DomainException(code, message);
    }

    public static DomainException erro() {
        return new DomainException("COD-01", "Descrição do erro.");
    }

    public static DomainException servicoRoteirizadorIndisponivel() {
        return new DomainException("COD-02",
                "Serviço indisponível no momento. Por favor, tente novamente em alguns instantes.",
                502);
    }

    public static DomainException tenantNaoAutorizado() {
        return new DomainException("COD-03",
                "O cabeçalho x-tenant-id é inválido, não foi preenchido ou seu usuário não tem permissão para utilizar esse x-tenant-id.",
                401);
    }

    public static DomainException algoNaoEncontrado() {
        return new DomainException("COD-04", "X coisa não encontrada", 404);
    }
    public static DomainException professorNaoEncontrado() {
        return new DomainException("COD-05", "Professor não encontrado.", 404);
    }

}
