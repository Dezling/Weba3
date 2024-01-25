package ru.itmo.validators;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("validatorForR")
public class ValidatorForR implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object obj) throws ValidatorException {
        try {
            float r = Float.parseFloat(obj.toString());
            if (r > 5 || r < 1) {
                throw new ValidatorException(message());
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(message());
        }

    }
    public FacesMessage message() {
        return new FacesMessage("Oшибка валидации. R должен быть [1,5] (°-v-°)");
    }
}
