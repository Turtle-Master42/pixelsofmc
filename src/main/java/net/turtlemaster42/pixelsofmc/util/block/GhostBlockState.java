package net.turtlemaster42.pixelsofmc.util.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GhostBlockState {

    private final Block block;
    private Map<Property<?>, Comparable<?>> states;

    public GhostBlockState(Block block) {
        this.block = block;
        this.states = new HashMap<>();
    }

    public GhostBlockState(Block block, Map<Property<?>, Comparable<?>> states) {
        this.block = block;
        this.states = states;
    }

    public GhostBlockState addProperty(Property<?> property, Comparable<?> value) {
        Collection<?> possibleValues = property.getPossibleValues();
        if (!this.block.defaultBlockState().hasProperty(property))
            throw new IllegalArgumentException("Cannot add property " + property + " as it does not exist in " + this.block);
        if (!possibleValues.contains(value))
            throw new ValueException(property.getName() + " can't contain value " + value.toString());
        this.states.put(property, value);
        return this;
    }

    public GhostBlockState removeProperty(Property<?> property) {
        this.states.remove(property);
        return this;
    }

    public boolean presentIn(BlockState blockState) {
        if (blockState.getBlock() != this.block) {
            return false;
        }
        for (Map.Entry<Property<?>, Comparable<?>> state : this.states.entrySet()) {
            PixelsOfMc.LOGGER.info("{}: !blockState.hasProperty({})={}, {} != {}={}", this.block, state.getKey(), !blockState.hasProperty(state.getKey()), blockState.getValue(state.getKey()), state.getValue(), blockState.getValue(state.getKey()) != state.getValue());
            if (!blockState.hasProperty(state.getKey())) {
                return false;
            } else if (blockState.getValue(state.getKey()) != state.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean is(Block block) {
        return this.block == block;
    }

    public boolean is(TagKey<Block> pTag) {
        return this.block.builtInRegistryHolder().is(pTag);
    }

//    public GhostBlockState copy() {
//        return new GhostBlockState(this.block, this.states);
//    }

    public boolean equals(BlockState blockState) {
        if (blockState.getBlock() != this.block) {
            return false;
        }
        if (blockState.getProperties().size() != this.states.size()) {
            return false;
        }

        for (Map.Entry<Property<?>, Comparable<?>> state : this.states.entrySet()) {
            if (!blockState.hasProperty(state.getKey())) {
                return false;
            } else if (blockState.getValue(state.getKey()) != state.getValue()) {
                return false;
            }
        }
        return true;
    }

    public <T extends Comparable<T>> T getValue(Property<T> pProperty) {
        Comparable<?> comparable = this.states.get(pProperty);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot get property " + pProperty + " as it does not exist in " + this.block);
        } else {
            return pProperty.getValueClass().cast(comparable);
        }
    }

    public Block getBlock() {
        return block;
    }

    //TODO might want to see if there is a way to do this without an unchecked cast
    public static <T extends Comparable<T>> Property<T> getProperty(Property<?> property) {
        return (Property<T>) property;
    }

    public String toString() {
        if (this.states.isEmpty()) {
            return this.block.toString();
        } else {
            StringBuilder text = new StringBuilder(this.block + "[");
            for (Map.Entry<Property<?>, Comparable<?>> state : this.states.entrySet()) {
                text.append(state.getKey().getName()).append("=").append(state.getValue()).append(",");
            }
            text.replace(text.length() - 1, text.length(), "]");

            return text.toString();
        }
    }

    public BlockState toBlockState() {
        BlockState blockState = this.block.defaultBlockState();
        for (ImmutableMap.Entry<Property<?>, Comparable<?>> state : this.states.entrySet()) {
            if (!blockState.hasProperty(state.getKey())) {
                continue;
            }
            blockState = blockState.setValue(GhostBlockState.getProperty(state.getKey()), this.getValue(state.getKey()));
        }
        return blockState;
    }

    public static GhostBlockState reapGhostBlockState(BlockState blockState) {
        BlockState defaultState = blockState.getBlock().defaultBlockState();
        GhostBlockState ghostState = new GhostBlockState(blockState.getBlock());

        for (Property<?> property : defaultState.getProperties()) {
            if (defaultState.getValue(property) == blockState.getValue(property)) {
                continue;
            }
            ghostState.addProperty(property, blockState.getValue(property));
        }
        return ghostState; //should output a GhostBlockState only containing the non default values of the BlockState
    }
}
