package me.zuif.rean.api.desc;

public enum DescBasicField {
    REALISTIC_NAME("realisticname"),
    GENDER("gender"),
    AGE("age");
    private final String name;

    DescBasicField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
