package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.validators;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductCreateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductUpdateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class ProductValidation implements Validator {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageSource messageSource; // Me permite traerme el contenido que allá en los archivos .properties

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreateDto.class.isAssignableFrom(clazz) || ProductUpdateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof ProductCreateDto) {
            validateCreate((ProductCreateDto) target, errors);
        } else if (target instanceof ProductUpdateDto) {
            validateUpdate((ProductUpdateDto) target, errors);
        }
    }

    private void validateCreate(ProductCreateDto dto, Errors errors) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.rejectValue("name", "productCreateDto.name.notBlank", messageSource.getMessage("productCreateDto.name.notBlank", null, Locale.getDefault()));
        }

        if (dto.getPrice() != null && dto.getPrice() < 0) {
            errors.rejectValue("price", "productCreateDto.price.positive", messageSource.getMessage("productCreateDto.price.positive", null, Locale.getDefault()));
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "productCreateDto.description.notBlank", messageSource.getMessage("productCreateDto.description.notBlank", null, Locale.getDefault()));
        }
        if (dto.getSku() == null || dto.getSku().trim().isEmpty()) {
            errors.rejectValue("sku", "productCreateDto.sku.notBlank", messageSource.getMessage("productCreateDto.sku.notBlank", null, Locale.getDefault()));
        }
        if (productRepository.existsBySku(dto.getSku())) {
            errors.rejectValue("sku", "productCreateDto.sku.duplicate", "Ya existe!!!");
        }
    }

    private void validateUpdate(ProductUpdateDto dto, Errors errors) {

        if (dto.getPrice() != null && dto.getPrice() < 0) {
            errors.rejectValue("description", "productUpdateDto.price.positive", messageSource.getMessage("productUpdateDto.price.positive", null, Locale.getDefault()));
        }
//        SKU no tiene etiquetas(@) para validar, todo se esta validando desde esta clase y los mensaje personalizados del fichero personalized.properties.
        if (dto.getSku() == null || dto.getSku().trim().isBlank()) {
            errors.rejectValue("sku", "productUpdateDto.sku.notBlank", messageSource.getMessage("productUpdateDto.sku.notBlank", null, Locale.getDefault()));
        }
        if (productRepository.existsBySku(dto.getSku())) {
            errors.rejectValue("sku", "productUpdateDto.sku.existsSku", messageSource.getMessage("productUpdateDto.sku.existsSku", null, Locale.getDefault()));
        }
    }
}
