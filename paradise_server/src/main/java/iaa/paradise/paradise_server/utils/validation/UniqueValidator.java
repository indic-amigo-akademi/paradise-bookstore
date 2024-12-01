package iaa.paradise.paradise_server.utils.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> serviceClass = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, serviceClass);
        } else {
            this.service = this.applicationContext.getBean(serviceClass);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return !this.service.fieldValueExists(this.fieldName, value);
    }

}
