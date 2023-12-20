package com.mateusjose98.management.model.enums;
public enum VerificationType {
    ACCOUNT("ACCOUNT"),
    PASSWORD("PASSWORD");

    private final String type;

    VerificationType(String type) { this.type = type; }

    public String getType() {
        return this.type.toLowerCase();
    }
}
