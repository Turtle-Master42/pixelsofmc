package net.turtlemaster42.pixelsofmc.util.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
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

    public <T extends Comparable<T>, V extends T> GhostBlockState addProperty(Property<T> property, V value) {
        Collection<?> possibleValues = property.getPossibleValues();
        if (!this.block.defaultBlockState().hasProperty(property))
            throw new IllegalArgumentException(this.block + " can't contain property: " + property.getName());
        if (!possibleValues.contains(value))
            throw new ValueException(property.getName() + " can't contain value: " + value.toString());
        this.states.put(property, value);
        return this;
    }

    public <T extends Comparable<T>> GhostBlockState removeProperty(Property<T> property) {
        this.states.remove(property);
        return this;
    }

    public boolean presentIn(BlockState blockState) {
        if (blockState.getBlock() != this.block) {
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

    public boolean is(Block block) {
        return this.block == block;
    }

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
}
