package me.zuif.rean.api.config.file;

import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.wrapper.*;
import me.zuif.rean.api.util.Range;
import me.zuif.rean.api.util.TripleKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalsConfig extends AbstractConfiguration {
    private final Animal type;
    private Map<TripleKey<Age, Gender, AgeGenderRangeType>, AgeGenderConfigRange> ageGenderRanges;
    private List<ConfigAge> ages;
    private List<ConfigGender> genders;
    private List<ConfigGenderChance> genderChances;
    private Map<ConfigGender, List<Gender>> breedCompatibility;
    private Map<RangeType, ConfigRange> ranges;

    public AnimalsConfig(Animal type) {
        super("/animals/", type.getConfigName() + ".yml", true);
        this.type = type;
        this.ages = new ArrayList<>();
        this.genders = new ArrayList<>();
        this.ranges = new HashMap<>();
        this.ageGenderRanges = new HashMap<>();
        this.genderChances = new ArrayList<>();
        this.breedCompatibility = new HashMap<>();
    }

    public boolean checkCompatibility(RealisticAnimal father, RealisticAnimal mother) {
        for (Gender gender : breedCompatibility.get(getConfigGender(father.getGender().getName()))) {
            if (mother.getGender().getName().equals(gender.getName())) return true;
        }
        return false;
    }

    private ConfigGender getConfigGender(String name) {
        return genders.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }

    public Animal getType() {
        return type;
    }

    public Map<RangeType, ConfigRange> getRanges() {
        return ranges;
    }

    public void setRanges(Map<RangeType, ConfigRange> ranges) {
        this.ranges = ranges;
    }

    public List<ConfigAge> getAges() {
        return ages;
    }

    public void setAges(List<ConfigAge> ages) {
        this.ages = ages;
    }

    public List<ConfigGender> getGenders() {
        return genders;
    }

    public void setGenders(List<ConfigGender> genders) {
        this.genders = genders;
    }

    public Range getRange(RangeType rangeType) {
        return ranges.get(rangeType);
    }

    public Map<TripleKey<Age, Gender, AgeGenderRangeType>, AgeGenderConfigRange> getAgeGenderRanges() {
        return ageGenderRanges;
    }

    public void setAgeGenderRanges(Map<TripleKey<Age, Gender, AgeGenderRangeType>, AgeGenderConfigRange> ageGenderRanges) {
        this.ageGenderRanges = ageGenderRanges;
    }

    public Map<ConfigGender, List<Gender>> getBreedCompatibility() {
        return breedCompatibility;
    }

    public void setBreedCompatibility(Map<ConfigGender, List<Gender>> breedCompatibility) {
        this.breedCompatibility = breedCompatibility;
    }

    @Override
    public void save() {
        Saver saver = new Saver();
        saver.saveAges();
        saver.saveGenders();
        saver.saveGenderChances();
        saver.saveRanges();
        saver.saveGenderAgeRanges();
        saver.saveBreedCompatibility();
        super.save();
    }

    @Override
    public void reload() {
        super.reload();
        Loader loader = new Loader();
        loader.loadAges();
        loader.loadGenders();
        loader.loadRanges();
        loader.loadGenderChances();
        loader.loadGenderAgeRanges();
        loader.loadBreedCompatibility();
    }

    public Range getAgeGenderRange(Age age, Gender gender, AgeGenderRangeType ranges) {
        TripleKey<Age, Gender, AgeGenderRangeType> key = new TripleKey<>(age, gender, ranges);
        return getAgeGenderRanges().get(key).getRange();
    }

    public List<ConfigGenderChance> getGenderChances() {
        return genderChances;
    }

    public void setGenderChances(List<ConfigGenderChance> genderChances) {
        this.genderChances = genderChances;
    }

    public Age parseAge(long currentAge) {
        Age result = null;
        for (Age age : getAges()) {
            if (currentAge >= age.getAgeInTicks()) {
                result = age;
            }
        }
        return result;
    }

    public enum RangeType {
        SPAWN_AGE("spawn-age"),
        GROW_AGE("grow-age"),
        DEATH_AGE("death-age"),
        FOLLOW_PARENT_SPEED("follow-parent-speed"),
        SPAWN_TRUST_VALUE("spawn-trust-value"),
        TRUST("trust");
        private final String path;

        RangeType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public enum AgeGenderRangeType {
        MAX_HEALTH("max-health"),
        MOVEMENT_SPEED("movement-speed"),
        DAMAGE("damage"),
        AVOID_DISTANCE("avoid.distance"),
        AVOID_SPEED_WALK("avoid.speed.walk"),
        AVOID_SPEED_SPRINT("avoid.speed.sprint"),
        LOOK_AT_PLAYER_DISTANCE("look-at-player-distance"),
        SPEED_PANIC("speed.panic"),
        SPEED_BREED("speed.breed"),
        SPEED_BAIT("speed.bait"),
        SPEED_WATER_STROLL_AVOID("speed.water-stroll-avoid");
        private final String path;

        AgeGenderRangeType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private class Saver {
        private void saveAges() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (ConfigAge age : getAges()) {
                cfg.set(age.getPath(), age.getAge());
            }

        }

        private void saveGenderAgeRanges() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (AgeGenderConfigRange range : getAgeGenderRanges().values()) {
                ConfigRange configRange = range.getRange();
                String path = configRange.getPath();
                cfg.set(path + ".start", configRange.getStart());
                cfg.set(path + ".end", configRange.getEnd());
            }
        }

        private void saveGenders() {
            YamlConfiguration cfg = getYamlConfiguration();
            cfg.set("genders", getGenders().stream().map(Gender::getName).toList());
        }

        private void saveRanges() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (ConfigRange configRange : getRanges().values()) {
                String path = configRange.getPath();
                cfg.set(path + ".start", configRange.getStart());
                cfg.set(path + ".end", configRange.getEnd());
            }
        }

        private void saveGenderChances() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (ConfigGenderChance genderChance : getGenderChances()) {
                cfg.set(genderChance.getPath(), genderChance.getValue());
            }
        }

        private void saveBreedCompatibility() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (Map.Entry<ConfigGender, List<Gender>> entry : breedCompatibility.entrySet()) {
                cfg.set(entry.getKey().getPath(), entry.getValue().stream().map(Gender::getName).toList());
            }
        }
    }

    private class Loader {
        private void loadBreedCompatibility() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (Gender gender : getGenders()) {
                String path = "breed-compatibility." + gender.getName();
                List<String> genders = cfg.getStringList(path);
                breedCompatibility.put(new ConfigGender(gender.getName(), path), genders.stream().map(Gender::new).toList());
            }
        }

        private void loadAges() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (String s : cfg.getConfigurationSection("ages").getKeys(false)) {
                ConfigAge configAge = new ConfigAge(cfg.getDouble("ages." + s), s, "ages." + s);
                getAges().add(configAge);
            }
        }

        private void loadGenderAgeRanges() {

            for (ConfigGender gender : getGenders()) {
                String genderName = gender.getName();
                for (ConfigAge age : getAges()) {
                    String ageName = age.getDescription();
                    String currentPath = "age-gender-ranges." + genderName + "." + ageName + ".";
                    for (AgeGenderRangeType genderRanges : AgeGenderRangeType.values()) {
                        String path = currentPath + genderRanges.getPath();
                        getAgeGenderRanges().put(new TripleKey<>(age, gender, genderRanges),
                                new AgeGenderConfigRange(age, gender, loadGenderConfigRange(path)));
                    }
                }
            }
        }

        private void loadGenderChances() {
            YamlConfiguration cfg = getYamlConfiguration();
            for (ConfigGender gender : getGenders()) {
                getGenderChances().add(new ConfigGenderChance(cfg.getDouble("gender-chances." + gender.getName()),
                        gender, "gender-chances." + gender.getName()));
            }
        }

        private void loadGenders() {
            YamlConfiguration cfg = getYamlConfiguration();
            getGenders().addAll(cfg.getStringList("genders").stream().map(s -> new ConfigGender(s, "genders")).
                    toList());
        }

        private void loadRanges() {
            for (RangeType rangeTypeName : RangeType.values()) {
                String path = "ranges." + rangeTypeName.getPath();
                ConfigRange range = new ConfigRange(getYamlConfiguration().getDouble(path + ".start"),
                        getYamlConfiguration().getDouble(path + ".end"), path);
                getRanges().put(rangeTypeName, range);
            }
        }

        private ConfigRange loadGenderConfigRange(String path) {
            return new ConfigRange(getYamlConfiguration().getDouble(path + ".start"),
                    getYamlConfiguration().getDouble(path + ".end"), path);
        }
    }
}
