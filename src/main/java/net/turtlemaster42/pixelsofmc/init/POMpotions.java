package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POMpotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, PixelsOfMc.MOD_ID);

//    public static final RegistryObject<Potion> SLIMEY_POTION = POTIONS.register("slimey_potion",
//            () -> new Potion(new MobEffectInstance(MobEffects.JUMP, 200, 0)));

    public static final class ExtendedPotions {
        public static final Map<Potion, RegistryObject<Potion>> EXTENDED_POTIONS = new HashMap<>();
        public static final Map<Potion, RegistryObject<Potion>> BUFFED_POTIONS = new HashMap<>();

        private static void init() {
            List<String> registeredKeys = new ArrayList<>();
            for(Map.Entry<ResourceKey<Potion>, Potion> registeredPotion : ForgeRegistries.POTIONS.getEntries()) {
                registeredKeys.add(registeredPotion.getKey().location().toString());
            }

            for(Map.Entry<ResourceKey<Potion>, Potion> registeredPotion : ForgeRegistries.POTIONS.getEntries()) {
                Potion potion = registeredPotion.getValue();
                if (potion.getEffects().isEmpty()) {continue;}

                String resourceLocation = registeredPotion.getKey().location().toString();
                String key = resourceLocation.split(":")[1];
                String name = potion.getName("");

                if (key.contains("extra_")) {continue;}

                if (key.contains("long_")) {
                    RegistryObject<Potion> NEW_POTION = POTIONS.register("extra_" + key, () -> createPotion(name, potion.getEffects(), 3f, 0));
                    EXTENDED_POTIONS.put(potion, NEW_POTION);
                } else if (key.contains("strong_")) {
                    RegistryObject<Potion> NEW_POTION = POTIONS.register("extra_" + key, () -> createPotion(name, potion.getEffects(), 2/3f, 1));
                    BUFFED_POTIONS.put(potion, NEW_POTION);
                    if (registeredKeys.contains(resourceLocation.replace(":strong", ":long"))) {
                        NEW_POTION = POTIONS.register("long_" + key, () -> createPotion(name, potion.getEffects(), 2.5f, 0));
                        EXTENDED_POTIONS.put(potion, NEW_POTION);
                    }
                }
            }
        }
    }

    public static void register(IEventBus eventBus) {
        PixelsOfMc.LOGGER.info("registering POM potions");
        PixelsOfMc.LOGGER.info(ForgeRegistries.POTIONS.getEntries().toString());
        POTIONS.register(eventBus);
        ExtendedPotions.init();
    }

    public static void addMixes() {
        for (Potion basePotion : ExtendedPotions.EXTENDED_POTIONS.keySet()) {
            Potion registeredPotion = ExtendedPotions.EXTENDED_POTIONS.get(basePotion).get();
            PotionBrewing.addMix(basePotion, POMitems.CRIMSON_MANA_SPHERE.get(), registeredPotion);
        }
        for (Potion basePotion : ExtendedPotions.BUFFED_POTIONS.keySet()) {
            Potion registeredPotion = ExtendedPotions.BUFFED_POTIONS.get(basePotion).get();
            PotionBrewing.addMix(basePotion, POMitems.GLEAMING_MANA_SPHERE.get(), registeredPotion);
        }
    }

    public static Potion createPotion(String name, List<MobEffectInstance> effectInstances, float durationMultiplier, int amplifierBonus) {
        int effectCount = effectInstances.size();
        MobEffectInstance[] effects = new MobEffectInstance[effectCount];

        for (int i = 0; i < effectCount; i++) {
            MobEffectInstance instance = effectInstances.get(i);
            effects[i] = new MobEffectInstance(instance.getEffect(), (int) (instance.getDuration() * durationMultiplier), instance.getAmplifier() + amplifierBonus);
        }
        return new Potion(name, effects);
    }
}
