package ru.itmo.validators;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("validatorForX")
public class validatorForX implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        if(obj == null){
            throw new ValidatorException(message(2));
        }
        try {
            float r = Float.parseFloat(obj.toString());
            if (r > 2 || r < -2) {
                throw new ValidatorException(message(1));
            }
        } catch (NumberFormatException | NullPointerException e) {
            throw new ValidatorException(message(2));
        }
    }
    public FacesMessage message(int numberMessage) {
        if(numberMessage == 1) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oшибка валидации. X должен быть [-2,2] (°-v-°)", null);
        }
        else{
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oшибка валидации. X должен быть числом или не пустым (°-v-°)", null);
        }
        }
}
