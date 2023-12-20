package com.mateusjose98.management.model.enums;



public enum EventType {
    LOGIN_ATTEMPT("Você tentou logar"),
    LOGIN_ATTEMPT_FAILURE("Tentativa de login com falha"),
    LOGIN_ATTEMPT_SUCCESS("Tentativa de login com sucesso"),
    PROFILE_UPDATE("Atualizou as informações do perfil"),
    PROFILE_PICTURE_UPDATE("Atualizou a foto de perfil"),
    ROLE_UPDATE("Atualizou a função(role)"),
    ACCOUNT_SETTINGS_UPDATE("Atualizou as configurações"),
    MFA_UPDATE("Atualizou o MFA"),
    PASSWORD_UPDATE("Atualizou a senha");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
