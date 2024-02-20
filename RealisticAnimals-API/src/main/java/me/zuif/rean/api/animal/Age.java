package me.zuif.rean.api.animal;


import me.zuif.rean.api.util.TimeConverter;

public class Age {
    private long ageInTicks;
    private double age;
    private String description;

    public Age(double age, String description) {
        this.age = age;
        this.description = description;
        this.ageInTicks = TimeConverter.convertYearsToTicks(age);
    }

    public static Age deserialize(String serialized) {
        String[] args = serialized.split(",");
        return new Age(Double.parseDouble(args[0]), args[1]);
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public long getAgeInTicks() {
        return ageInTicks;
    }

    public void setAgeInTicks(long ageInTicks) {
        this.ageInTicks = ageInTicks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String serialize() {
        return age + "," + description;
    }
}
