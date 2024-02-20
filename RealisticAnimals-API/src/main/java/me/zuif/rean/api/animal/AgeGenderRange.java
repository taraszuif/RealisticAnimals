package me.zuif.rean.api.animal;


import me.zuif.rean.api.util.Range;

public class AgeGenderRange {
    private Age age;
    private Gender gender;
    private Range range;

    public AgeGenderRange(Age age, Gender gender, Range range) {
        this.age = age;
        this.gender = gender;
        this.range = range;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
