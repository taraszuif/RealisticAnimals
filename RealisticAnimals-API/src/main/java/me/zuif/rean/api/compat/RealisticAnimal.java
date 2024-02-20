package me.zuif.rean.api.compat;

import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.handler.BaseHandler;

import java.util.UUID;

public interface RealisticAnimal {
    Age getRealisticAge();

    Gender getGender();

    String getRealisticName();

    void setRealisticName(String newName);

    Animal getRealisticType();


    void load();

    UUID getID();

    BaseHandler getHandler();

}
