package online.foundfave.foundfaveapi.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FieldErrorHandling {

    public static String showFieldErrors(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return sb.toString();
    }
}



