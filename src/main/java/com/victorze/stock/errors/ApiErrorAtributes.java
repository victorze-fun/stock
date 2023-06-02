package com.victorze.stock.errors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

// personaliza el mensaje de error cuando lanzamos ResponseStatusException en los controladores
// es mas moderno y es excluyente con @RestControllerAdvice
// se recomienda extender ResponseStatusException con una api mas corta, para facilitar su uso
@Component
public class ApiErrorAtributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> allErrorAttributes = super.getErrorAttributes(webRequest, options);

        Map<String, Object> errorAttributes = new HashMap<>();
        int statusCode = (int) allErrorAttributes.get("status");
        errorAttributes.put("state", HttpStatus.valueOf(statusCode));
        errorAttributes.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        String mensaje = "";

        Throwable throwable = getError(webRequest);

        if (throwable instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) throwable;
            mensaje = responseStatusException.getReason() == null ? "" : responseStatusException.getReason();
        } else {
            if (throwable.getCause() != null)
                mensaje = throwable.getCause().getMessage() == null ? throwable.getCause().toString()
                        : throwable.getCause().getMessage();
            else
                mensaje = throwable.toString();
        }

        errorAttributes.put("message", mensaje);

        // var errors = Map.of("one", 1, "dos", 2, "tres", 3);
        // errorAttributes.put("messages", errors);

        // var error1 = Map.of("one", 1, "dos", 2);
        // var error2 = Map.of("one", 1, "dos", 2, "tres", 3);
        // var listErrors = List.of(error1, error2);
        // errorAttributes.put("messagesList", listErrors);

        return errorAttributes;
    }

}
