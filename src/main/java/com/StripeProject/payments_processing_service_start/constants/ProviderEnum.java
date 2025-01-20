package com.StripeProject.payments_processing_service_start.constants;

import lombok.Getter;

public enum ProviderEnum {

    STRIPE(1, "STRIPE");

    @Getter
    private int id;

    @Getter
    private String name;

    ProviderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProviderEnum getById(int id) {
        for (ProviderEnum method : ProviderEnum.values()) {
            if (method.getId() == id) {
                return method;
            }
        }

        System.out.println("Invalid ProviderEnum| id:" + id);
        return null;
    }

    public static ProviderEnum getByName(String name) {
        for (ProviderEnum method : ProviderEnum.values()) {
            if (method.getName().equalsIgnoreCase(name)) {
                System.out.println(method.getName());
                System.out.println(method);
                return method;
            }
        }

        System.out.println("Invalid ProviderEnum| name:" + name);
        return null;
    }

    @Override
    public String toString() {
        return name();
    }
}
