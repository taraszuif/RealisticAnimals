package me.zuif.rean.api.handler;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.file.AnimalsConfig;
import me.zuif.rean.api.util.RealisticNameGenerator;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class DataHandler {
    private final Entity animal;


    public DataHandler(Entity animal) {
        this.animal = animal;
    }

    public static void copyInfo(Entity from, Entity to) {

        DataHandler fromHandler = new DataHandler(from);
        boolean realistic = fromHandler.isRealistic();
        System.out.println("from real: " + realistic);
        if (!realistic) return;
        System.out.println("from handler: " + fromHandler.loadRealisticName() + " " + fromHandler.loadRealisticAge());
        DataHandler toHandler = new DataHandler(to);

        toHandler.saveRealisticData(fromHandler.loadRealisticData(Animal.getByType(from.getType())));
    }

    public Entity getAnimal() {
        return animal;
    }

    private Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("RealisticAnimals");
    }

    private NamespacedKey getKey(String key) {
        return new NamespacedKey(getPlugin(), "realistic");
    }

    public boolean isRealistic() {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), "realistic");
        return container.has(key, PersistentDataType.BYTE) && container.get(key, PersistentDataType.BYTE) == 1;
    }

    public void saveRealistic(boolean realistic) {
        saveBoolean(realistic, "realistic");
    }

    public void saveRealisticData(BaseHandler handler) {
        AgeHandler ageHandler = handler.getAgeHandler();
        GenderHandler genderHandler = handler.getGenderHandler();
        RangeHandler rangeHandler = handler.getRangeHandler();
        String realisticName = handler.getRealisticName();

        //AgeHandler
        saveLiveTicks(ageHandler.getLiveTicks());
        saveDeathAge(ageHandler.getDeathAge());
        saveRealisticAge(ageHandler.getAge().serialize());
        savePregnantEndTime(ageHandler.getPregnantEndTime());
        savePregnant(ageHandler.isPregnant());
        //GenderHandler
        saveGender(genderHandler.getGender().getName());
        //RangeHandler
        for (AnimalsConfig.AgeGenderRangeType type : AnimalsConfig.AgeGenderRangeType.values()) {
            saveAgeGenderRange(type, rangeHandler.getAgeGender(type));
        }
        for (AnimalsConfig.RangeType type : AnimalsConfig.RangeType.values()) {
            saveRange(type, rangeHandler.getRange(type));
        }
        //RealisticName
        saveRealisticName(realisticName);

        saveRealistic(true);
    }

    public BaseHandler loadRealisticData(Animal animalType) {
        if (isRealistic()) {
            System.out.println("realistc");
            //AgeHandler
            long liveTicks = loadLiveTicks();
            long deathAge = loadDeathAge();
            Age realisticAge = Age.deserialize(loadRealisticAge());
            long pregnantEndTime = loadPregnantEndTime();
            boolean pregnant = loadPregnant();
            AgeHandler ageHandler = new AgeHandler(new ConfigHandler(animalType), pregnantEndTime, pregnant, liveTicks, realisticAge, deathAge);
            //GenderHandler
            Gender gender = new Gender(loadGender());
            GenderHandler genderHandler = new GenderHandler(gender);
            //RangeHandler
            Map<AnimalsConfig.AgeGenderRangeType, Double> map = new HashMap<>();
            for (AnimalsConfig.AgeGenderRangeType type : AnimalsConfig.AgeGenderRangeType.values()) {
                map.put(type, loadAgeGenderRange(type));
            }
            Map<AnimalsConfig.RangeType, Double> rangeMap = new HashMap<>();
            for (AnimalsConfig.RangeType type : AnimalsConfig.RangeType.values()) {
                rangeMap.put(type, loadRange(type));
            }
            RangeHandler rangeHandler = new RangeHandler(map, rangeMap);
            //RealisticName
            String realisticName = loadRealisticName();
            BaseHandler baseHandler = new BaseHandler(animalType, realisticName, ageHandler,
                    genderHandler, new GoalsHandler(), rangeHandler);
            return baseHandler;
        } else {
            System.out.println("not realistic");
            return new BaseHandler(animalType, new RealisticNameGenerator(animal).generate());
        }
    }

    private void saveAgeGenderRange(AnimalsConfig.AgeGenderRangeType type, double value) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), type.getPath().replaceAll("\\.", "_"));
        container.set(key, PersistentDataType.DOUBLE, value);
    }

    private double loadAgeGenderRange(AnimalsConfig.AgeGenderRangeType type) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), type.getPath().replaceAll("\\.", "_"));
        return container.get(key, PersistentDataType.DOUBLE);
    }

    private double loadRange(AnimalsConfig.RangeType type) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), type.getPath().replaceAll("\\.", "_"));
        return container.get(key, PersistentDataType.DOUBLE);
    }

    private void saveRange(AnimalsConfig.RangeType type, double value) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), type.getPath().replaceAll("\\.", "_"));
        container.set(key, PersistentDataType.DOUBLE, value);
    }

    private long loadLiveTicks() {
        return loadLong("liveTicks");
    }

    private long loadPregnantEndTime() {
        return loadLong("pregnantEndTime");
    }

    private long loadDeathAge() {
        return loadLong("deathAge");
    }

    private String loadRealisticName() {
        return loadString("realisticName");
    }

    private String loadGender() {
        return loadString("gender");
    }

    private String loadRealisticAge() {
        return loadString("realisticAge");
    }

    private String loadString(String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);

        return container.get(key, PersistentDataType.STRING);
    }

    private long loadLong(String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);
        return container.get(key, PersistentDataType.LONG);
    }

    private boolean loadPregnant() {
        return loadBoolean("pregnant");
    }

    private boolean loadBoolean(String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);
        return container.get(key, PersistentDataType.BYTE) == 1;
    }

    private void savePregnant(boolean pregnant) {
        saveBoolean(pregnant, "pregnant");
    }

    private void savePregnantEndTime(long pregnantEndTime) {
        saveLong(pregnantEndTime, "pregnantEndTime");
    }

    private void saveLiveTicks(long liveTicks) {
        saveLong(liveTicks, "liveTicks");
    }

    private void saveRealisticName(String realisticName) {
        saveString(realisticName, "realisticName");
    }

    private void saveGender(String gender) {
        saveString(gender, "gender");
    }

    private void saveRealisticAge(String age) {
        saveString(age, "realisticAge");
    }

    private void saveString(String value, String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);
        container.set(key, PersistentDataType.STRING, value);
    }

    private void saveBoolean(boolean value, String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);
        container.set(key, PersistentDataType.BYTE, value ? (byte) 1 : (byte) 0);
    }

    private void saveDeathAge(long deathAge) {
        saveLong(deathAge, "deathAge");
    }

    private void saveLong(long value, String targetKey) {
        PersistentDataContainer container = animal.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(getPlugin(), targetKey);
        container.set(key, PersistentDataType.LONG, value);
    }

    public static class LoadModule {
        private static final List<UUID> loadedEntities = new ArrayList<>();

        public static void addLoaded(UUID entity) {
            loadedEntities.add(entity);
            System.out.println("added " + entity);
        }

        public static void removeLoaded(UUID entity) {
            loadedEntities.remove(entity);
        }

        public static void removeAndSave(UUID uuid) {
            if (!loadedEntities.contains(uuid)) return;
            Entity entity = Bukkit.getEntity(uuid);
            if (!(entity instanceof Animals)) return;
            if (!(ReAnAPI.getInstance().getCompatManager().getOverrideHandler().isRealisticAnimal((LivingEntity) entity)))
                return;
            RealisticAnimal realisticAnimal = ReAnAPI.getInstance().getCompatManager().getOverrideHandler().getRealisticAnimal((LivingEntity) entity).get();
            DataHandler dataHandler = new DataHandler(entity);
            dataHandler.saveRealisticData(realisticAnimal.getHandler());
            System.out.println("Saving " + uuid);
            loadedEntities.remove(entity);
        }

        public static void removeAndSaveAll() {
            for (UUID uuid : loadedEntities) {
                removeAndSave(uuid);
            }
        }
    }


}
