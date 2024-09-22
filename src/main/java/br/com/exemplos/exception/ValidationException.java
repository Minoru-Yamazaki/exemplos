package br.com.exemplos.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String code;

    private final int statusCode;

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
        this.statusCode = 422;
    }

    public static ValidationException of(String message) {
        return new ValidationException("VPO-Validation", message);
    }

    public static ValidationException dataInicioAntesDataFim() {
        return new ValidationException("VPO-Validation", "A dataInicioViagem deve ser anterior a dataFimViagem.");
    }

    public static ValidationException vigenciaNaoSuperior30Dias() {
        return new ValidationException("VPO-Validation", "A vigência da viagem não deve ser superior a 30 dias.");
    }

    public static void validaQuantidadeDeCaracteres(String campo, String nomeCampo, int limite) {
        if (campo.length() > limite)
            throw ValidationException.quantidadeDeCaracteresExcedido(nomeCampo, limite);
    }

    public static ValidationException quantidadeDeCaracteresExcedido(String campo, int limite) {
        return new ValidationException("VPO-Validation", "Limite máximo para o campo ".concat(campo).concat(" é de: ")
                .concat(String.valueOf(limite)).concat(" caracteres."));
    }

    public static ValidationException dataAnteriorAtual(String campo) {
        return new ValidationException("VPO-Validation", "O campo " + campo + " não deve ser anterior a data atual.");
    }

    public static ValidationException dataHoraAnteriorAtual(String campo) {
        return new ValidationException("VPO-Validation",
                "O campo " + campo + " não deve ser anterior a data e hora atual.");
    }

    public static ValidationException veiculoNaoInformado() {
        return new ValidationException("VPO-Validation", "O campo veiculo é obrigatório.");
    }

    public static class Message {

        public static final String PLACA_OBRIGATORIA = "Placa do veículo não informada, favor informar os dados.";

        public static final String PLACA_INVALIDA = "Formato de placa incorreto, por favor, verifique os dados informados.";

    }

}
