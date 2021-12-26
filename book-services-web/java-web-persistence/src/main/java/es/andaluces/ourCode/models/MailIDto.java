package es.andaluces.ourCode.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MailIDto {

    @NotBlank
    private String email;
}
