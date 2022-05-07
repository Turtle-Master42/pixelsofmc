package net.mcreator.pixelsofmc.procedures;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.Gson;

public class DeformerActivateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double AmountInput = 0;
		double AmountOutput = 0;
		double MachineOutput = 0;
		double RecipeCount = 0;
		String ItemInput = "";
		String ItemOutput = "";
		String MachineInput = "";
		String MachineOutputItem = "";
		File recipe2 = new File("");
		com.google.gson.JsonObject mainJsonDeformer = new com.google.gson.JsonObject();
		if (!world.isClientSide()) {
			recipe2 = new File((FMLPaths.GAMEDIR.get().toString() + "/config/pixelsofminecraft/recipes/deformer"), File.separator + "vanilla.json");
			{
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(recipe2));
					StringBuilder jsonstringbuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						jsonstringbuilder.append(line);
					}
					bufferedReader.close();
					mainJsonDeformer = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
					RecipeCount = 0;
					AmountInput = new Object() {
						public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
							AtomicInteger _retval = new AtomicInteger(0);
							BlockEntity _ent = world.getBlockEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).getCount()));
							return _retval.get();
						}
					}.getAmount(world, new BlockPos(x, y, z), 0);
					MachineOutputItem = ("" + (new Object() {
						public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
							AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
							BlockEntity _ent = world.getBlockEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
							return _retval.get();
						}
					}.getItemStack(world, new BlockPos(x, y, z), 1))).substring((int) (("" + Math.round(MachineOutput)).length() + 1),
							(int) ("" + (new Object() {
								public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
									AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
									BlockEntity _ent = world.getBlockEntity(pos);
									if (_ent != null)
										_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
												.ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
									return _retval.get();
								}
							}.getItemStack(world, new BlockPos(x, y, z), 1))).length());
					MachineOutput = new Object() {
						public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
							AtomicInteger _retval = new AtomicInteger(0);
							BlockEntity _ent = world.getBlockEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).getCount()));
							return _retval.get();
						}
					}.getAmount(world, new BlockPos(x, y, z), 1);
					for (int index0 = 0; index0 < (int) (mainJsonDeformer.get("recipe_amount").getAsDouble()); index0++) {
						RecipeCount = RecipeCount + 1;
						ItemInput = mainJsonDeformer.get(("input_tag_" + Math.round(RecipeCount))).getAsString();
						ItemOutput = mainJsonDeformer.get(("output_" + Math.round(RecipeCount))).getAsString();
						AmountOutput = mainJsonDeformer.get(("amount_" + Math.round(RecipeCount))).getAsDouble();
						if ((ItemOutput.replace(" ", "") + " ").contains(MachineOutputItem.replace(" ", "") + " ")
								|| (MachineOutputItem).equals("air")) {
							if ((new Object() {
								public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
									AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
									BlockEntity _ent = world.getBlockEntity(pos);
									if (_ent != null)
										_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
												.ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
									return _retval.get();
								}
							}.getItemStack(world, new BlockPos(x, y, z), 0))
									.is(ItemTags.create(new ResourceLocation((ItemInput).toLowerCase(java.util.Locale.ENGLISH))))) {
								if (MachineOutput + AmountOutput <= 64) {
									{
										BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
										if (_ent != null) {
											final int _slotid = 0;
											final int _amount = 1;
											_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													ItemStack _stk = capability.getStackInSlot(_slotid).copy();
													_stk.shrink(_amount);
													((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _stk);
												}
											});
										}
									}
									if (world instanceof ServerLevel _level)
										_level.getServer().getCommands().performCommand(
												new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "",
														new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
												("item replace block " + Math.round(x) + " " + Math.round(y) + " " + Math.round(z)
														+ " container.1 with " + ItemOutput + " " + Math.round(MachineOutput + AmountOutput)));
									{
										BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
										if (_ent != null) {
											final int _slotid = 2;
											final int _amount = 1;
											_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													ItemStack _stk = capability.getStackInSlot(_slotid).copy();
													_stk.shrink(_amount);
													((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _stk);
												}
											});
										}
									}
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
