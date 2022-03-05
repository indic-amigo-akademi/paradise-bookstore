package com.iaa.paradise_server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
    public boolean isValid(Object val, ConstraintValidatorContext context) {
        return !this.service.fieldValueExists(this.fieldName, val);
    }

}
