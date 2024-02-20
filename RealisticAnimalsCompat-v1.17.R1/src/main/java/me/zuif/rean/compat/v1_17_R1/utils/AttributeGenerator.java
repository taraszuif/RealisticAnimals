package me.zuif.rean.compat.v1_17_R1.utils;

import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AttributeGenerator {
    private static Field attributeField;

    public static void registerGenericAttributes() {
        Class<DefaultAttributes> clazz = DefaultAttributes.class;

        // Получаем поле
        Field field = null; // Замените YOUR_FIELD_NAME на имя вашего поля
        try {
            field = clazz.getDeclaredField("b");


            // Разрешаем доступ к полю, даже если оно private
            field.setAccessible(true);

            // Проверяем, является ли поле final
            if (Modifier.isFinal(field.getModifiers())) {
                // Изменяем модификатор поля, чтобы снять final
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
