package org.ukdw.filter;

import org.ukdw.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class EmailValidation {

    private final AppProperties appProperties;

    public boolean emailIsValid(String email){
        if(Pattern.matches(appProperties.getEmailRegex().getTi(),email)||
                Pattern.matches(appProperties.getEmailRegex().getSi(),email)||
                Pattern.matches(appProperties.getEmailRegex().getStaff(),email)){
            return true;
        }
        return false;
    }
}
