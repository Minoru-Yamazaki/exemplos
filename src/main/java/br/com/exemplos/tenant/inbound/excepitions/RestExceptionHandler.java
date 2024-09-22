package br.com.exemplos.tenant.inbound.excepitions;

import br.com.exemplos.exception.DomainException;
import br.com.exemplos.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<ResponseWithMessage> handlePropertyReferenceException(PropertyReferenceException ex) {
        // Esta exceção ocorre quando um pageable é informado numa consulta
        // com uma propriedade que não existe na entidade.
        String message = "A propriedade '" + ex.getPropertyName()
                + "' não foi encontrada ou não é suportada para esta operação.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWithMessage.builder().codigo("VPO-38").mensagem(message).build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ResponseWithMessage> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        String message = "O campo " + ex.getParameterName() + " é obrigatório.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWithMessage.builder().codigo("VPO-01").mensagem(message).build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseWithMessageAndDetail> handleMessageNotReadable(
            HttpMessageNotReadableException ex) {
        String message = "Não foi possível realizar a solicitação devido a um ou mais erros de sintaxe. Corrija os erros e tente novamente.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWithMessageAndDetail.builder()
                .codigo("VPO-01").mensagem(message).detalhe(this.extractDetailedMessage(ex)).build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ResponseWithMessage> handleNoHandlerFoundException(NoHandlerFoundException ignoredEx) {
        String message = "Recurso não encontrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseWithMessage.builder().codigo("VPO-03").mensagem(message).build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Void> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ignoredEx) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(DomainException.class)
    protected ResponseEntity<ResponseWithMessage> handleDomainException(DomainException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ResponseWithMessage.builder().mensagem(ex.getMessage()).codigo(ex.getCode()).build());
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ResponseWithMessage> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ResponseWithMessage.builder().mensagem(ex.getMessage()).codigo(ex.getCode()).build());
    }

    @ExceptionHandler
    protected ResponseEntity<ResponseWithMessage> handleAnything(Exception ex) {
        String message = "Ocorreu um erro interno do servidor ao processar a solicitação. Tente novamente mais tarde.";

        log.info("{}. Exception: {}", message, this.convertExceptionToJson(ex));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseWithMessage.builder().codigo("VPO-04").mensagem(message).build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseWithMessageAndDetail> handleConversionError(
            MethodArgumentTypeMismatchException ex) {
        String message = "O campo " + ex.getName() + " com valor " + ex.getValue()
                + " não pôde ser lido ou não é válido.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWithMessageAndDetail.builder()
                .codigo("VPO-01").mensagem(message).detalhe(ex.getMostSpecificCause().getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseWithMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseWithMessage.builder()
                .codigo("VPO-Validation").mensagem(objectError.getDefaultMessage()).build());
    }

    /**
     * https://stackoverflow.com/a/64826189
     */
    private String extractDetailedMessage(Throwable e) {
        final String message = e.getMessage();
        if (message == null) {
            return "";
        }
        final int tailIndex = StringUtils.indexOf(message, "; nested exception is");
        if (tailIndex == -1) {
            return message;
        }
        return StringUtils.left(message, tailIndex);
    }

    public String convertExceptionToJson(Exception e) {
        ObjectMapper mapper = new ObjectMapper();
        ExceptionDetails details = new ExceptionDetails(e);

        try {
            return mapper.writeValueAsString(details);
        }
        catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
            return "{}";
        }
    }

    private static class ExceptionDetails {

        private String exceptionMessage;

        private String stackTrace;

        public ExceptionDetails(Exception e) {
            this.exceptionMessage = e.getMessage();
            this.stackTrace = getStackTraceAsString(e);
        }

        private String getStackTraceAsString(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public String getStackTrace() {
            return stackTrace;
        }

    }

}
