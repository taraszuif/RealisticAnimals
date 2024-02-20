package me.zuif.rean.compat.v1_17_R1.animals;

import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.file.AnimalsConfig;
import me.zuif.rean.api.handler.BaseHandler;
import me.zuif.rean.api.handler.DataHandler;
import me.zuif.rean.api.handler.RangeHandler;
import me.zuif.rean.api.logger.DebugLogger;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.util.UUID;

public class RealisticCow extends Cow implements RealisticAnimal {
    private BaseHandler handler;

    public RealisticCow(Level world, Entity from) {
        this(world);
        DataHandler.copyInfo(from, getBukkitEntity());
        load();
        this.registerGoals();
    }

    public RealisticCow(EntityType<? extends Cow> types, Level world) {
        this(world);
    }

    public RealisticCow(Level world) {
        super(EntityType.COW, world);
        System.out.println("Create");
        DebugLogger.log("Calling super. constructor. ");
        //  AttributeGenerator.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);

    }

    @Override
    public void load() {
        this.handler = new DataHandler(getBukkitEntity()).loadRealisticData(getRealisticType());
        RangeHandler rangeHandler = handler.getRangeHandler();
        DebugLogger.log(handler.getRealisticName() + ": gender: " + handler.getGenderHandler().getGender().getName() + ", age: " + handler.getAgeHandler().getAge().getDescription() + "-" + handler.getAgeHandler().getAge().getAge());
        setHealth((float) rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.MAX_HEALTH));
        setSpeed((float) rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.MOVEMENT_SPEED));

    }


    @Override
    protected void registerGoals() {
        if (handler == null) {
            return;
        }
        DebugLogger.log("registering goals for " + handler.getRealisticName());
        RangeHandler rangeHandler = handler.getRangeHandler();
        this.goalSelector.removeAllGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.SPEED_PANIC)));
        this.goalSelector.addGoal(2, new BreedGoal(this, rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.SPEED_BREED)));
        this.goalSelector.addGoal(3, new TemptGoal(this, rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.SPEED_BAIT), Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, rangeHandler.getRange(AnimalsConfig.RangeType.FOLLOW_PARENT_SPEED)));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.SPEED_WATER_STROLL_AVOID)));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, (float) rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.LOOK_AT_PLAYER_DISTANCE)));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new AvoidEntityGoal<>(this, Player.class, (float) rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.AVOID_DISTANCE),
                rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.AVOID_SPEED_WALK),
                rangeHandler.getAgeGender(AnimalsConfig.AgeGenderRangeType.AVOID_SPEED_SPRINT)));

        //      this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    private void updateGoals() {

    }

    private void timeHandler() {
        if (handler == null) return;
        handler.getAgeHandler().tick();
        if (handler.getAgeHandler().checkIfDead()) {
            this.getBukkitEntity().getHandle().hurt(DamageSource.OUT_OF_WORLD, 1000000);
            this.getBukkitEntity().remove();
            return;
        }
        updateGoals();
    }

    @Override
    public UUID getID() {
        return getUUID();
    }

    @Override
    public void inactiveTick() {
        super.inactiveTick();
        timeHandler();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        timeHandler();
    }

    @Override
    public Cow createChild(ServerLevel worldserver, AgeableMob entityageable) {
        Cow kid = new RealisticCow(EntityType.COW, worldserver);
        kid.setBaby(true);
        return kid;
    }

    @Override
    public Age getRealisticAge() {
        DebugLogger.log("getRealistic age " + handler.getAgeHandler().getAge().getAgeInTicks());
        return handler.getAgeHandler().getAge();
    }


    @Override
    public BaseHandler getHandler() {
        return handler;
    }

    @Override
    public Gender getGender() {
        return handler.getGenderHandler().getGender();
    }

    @Override
    public String getRealisticName() {
        return handler.getRealisticName();
    }

    @Override
    public void setRealisticName(String newName) {
        handler.setRealisticName(newName);
    }

    @Override
    public Animal getRealisticType() {
        return Animal.COW;
    }


}
