package net.turtlemaster42.pixelsofmc.block.entity;

        import net.minecraft.network.chat.TranslatableComponent;
        import net.minecraft.server.level.ServerLevel;
        import net.minecraft.world.level.LevelAccessor;
        import net.minecraft.world.level.block.state.properties.BlockStateProperties;
        import net.turtlemaster42.pixelsofmc.PixelsOfMc;
        import net.turtlemaster42.pixelsofmc.gui.menu.BallMillGuiMenu;
        import net.turtlemaster42.pixelsofmc.init.POMblockEntities;
        import net.turtlemaster42.pixelsofmc.init.POMitems;
        import net.turtlemaster42.pixelsofmc.init.POMmessages;
        import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
        import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
        import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
        import net.turtlemaster42.pixelsofmc.recipe.BallMillRecipe;
        import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipe;
        import net.minecraft.core.BlockPos;
        import net.minecraft.core.Direction;
        import net.minecraft.nbt.CompoundTag;
        import net.minecraft.network.chat.Component;
        import net.minecraft.network.chat.TextComponent;
        import net.minecraft.network.protocol.Packet;
        import net.minecraft.network.protocol.game.ClientGamePacketListener;
        import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
        import net.minecraft.world.Containers;
        import net.minecraft.world.MenuProvider;
        import net.minecraft.world.SimpleContainer;
        import net.minecraft.world.entity.player.Inventory;
        import net.minecraft.world.entity.player.Player;
        import net.minecraft.world.inventory.AbstractContainerMenu;
        import net.minecraft.world.inventory.ContainerData;
        import net.minecraft.world.item.*;
        import net.minecraft.world.level.Level;
        import net.minecraft.world.level.block.entity.BlockEntity;
        import net.minecraft.world.level.block.state.BlockState;
        import net.minecraftforge.common.capabilities.Capability;
        import net.minecraftforge.common.util.LazyOptional;
        import net.minecraftforge.energy.CapabilityEnergy;
        import net.minecraftforge.energy.IEnergyStorage;
        import net.minecraftforge.items.CapabilityItemHandler;
        import net.minecraftforge.items.IItemHandler;
        import net.minecraftforge.items.ItemStackHandler;
        import org.jetbrains.annotations.NotNull;
        import org.jetbrains.annotations.Nullable;
        import org.openjdk.nashorn.internal.objects.annotations.Property;

        import javax.annotation.Nonnull;
        import java.util.Optional;
        import java.util.Random;

        import static net.minecraft.core.particles.ParticleTypes.*;


public class BallMillBlockEntity extends AbstractMachineEntity {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedUpgrade = 0;
    private int energyUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 4096;
    private static final int energyConsumption = 512;

    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
        }
    };

    @Override
    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    private void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public BallMillBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMblockEntities.BALL_MILL.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return BallMillBlockEntity.this.progress;
                    case 1: return BallMillBlockEntity.this.maxProgress;
                    case 2: return BallMillBlockEntity.this.speedUpgrade;
                    case 3: return BallMillBlockEntity.this.capacity;
                    case 4: return BallMillBlockEntity.this.maxReceive;
                    case 5: return BallMillBlockEntity.this.energyStorage.getEnergyStored();
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: BallMillBlockEntity.this.progress = value; break;
                    case 1: BallMillBlockEntity.this.maxProgress = value; break;
                    case 2: BallMillBlockEntity.this.speedUpgrade = value; break;
                }
            }

            public int getCount() {
                return 6;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.pixelsofmc.ball_mill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new BallMillGuiMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);

        return compound;
    }

    //---RECIPE---//

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, BallMillBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(5).getCount();
            pBlockEntity.speedUpgradeCheck();
            pBlockEntity.energyUpgradeCheck();
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(speedAmount != 0 ? speedAmount * energyConsumption - pBlockEntity.energyUpgrade * speedAmount : energyConsumption);
            if (pBlockEntity.progress > 0 && !pState.getValue(BlockStateProperties.LIT)) {
                pState.setValue(BlockStateProperties.LIT, true);
            }
            if(pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
                craftItem(pBlockEntity);
                pState.setValue(BlockStateProperties.LIT, false);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(BallMillBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BallMillRecipe> match = level.getRecipeManager()
                .getRecipeFor(BallMillRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
                && hasToolsInToolSlot(entity);
    }

    private static boolean hasToolsInToolSlot(BallMillBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(3).getItem() == POMitems.TITANIUM_CIRCLE_SAW.get();
    }

    private static boolean hasPower(BallMillBlockEntity entity) {
        return entity.energyStorage.getEnergyStored() >= (energyConsumption - entity.energyUpgrade);
    }

    private static void craftItem(BallMillBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BallMillRecipe> match = level.getRecipeManager()
                .getRecipeFor(BallMillRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);
            entity.itemHandler.getStackInSlot(3).hurt(1, new Random(), null); //saw
            if (entity.itemHandler.getStackInSlot(1).getDamageValue() > entity.itemHandler.getStackInSlot(1).getMaxDamage()) { //removes saw if needed
                entity.itemHandler.extractItem(1,1, false);
            }
            if (match.get().getOutputChance() >= Math.random()) {
                entity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItem().getItem(),
                        entity.itemHandler.getStackInSlot(4).getCount() + (match.get().getOutputCount())));
            }

            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void speedUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(5).getItem() == POMitems.SPEED_UPGRADE.get()) {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(5).getCount();
        } else {
            this.speedUpgrade = 0;
        }
    }
    private void energyUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(6).getItem() == POMitems.ENERGY_UPGRADE.get()) {
            this.energyUpgrade = energyConsumption / 10 * this.itemHandler.getStackInSlot(6).getCount();
        } else {
            this.energyUpgrade = 0;
        }
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(4).getItem() == output.getItem() || inventory.getItem(4).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + count;
    }

    public void activeParticles(LevelAccessor world) {
        int x = this.getBlockPos().getX();
        int y = this.getBlockPos().getY();
        int z = this.getBlockPos().getZ();
        if (world instanceof ServerLevel level)
            level.sendParticles(CRIT, x, y, z, 10, 1, 1, 1, 0);
    }



    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy " + energyStorage.getEnergyStored() + " is higher than max " + energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at " + this.getBlockPos() + " was outside limits, energy reverted to 0");
        }
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public IEnergyStorage getEnergyStorage() { return energyStorage; }

}

