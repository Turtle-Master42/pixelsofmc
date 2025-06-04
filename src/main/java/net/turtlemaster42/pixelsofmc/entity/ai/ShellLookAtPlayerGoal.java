package net.turtlemaster42.pixelsofmc.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;

public class ShellLookAtPlayerGoal extends LookAtPlayerGoal {
    public ShellLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance);
    }
    public ShellLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability) {
        super(pMob, pLookAtType, pLookDistance, pProbability, false);
    }
    public ShellLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability, boolean pOnlyHorizontal) {
        super(pMob, pLookAtType, pLookDistance, pProbability, pOnlyHorizontal);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof RiverShellEntity shell) {
            return super.canUse() && !shell.isInShell();
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob instanceof RiverShellEntity shell) {
            return super.canContinueToUse() && !shell.isInShell();
        }
        return super.canContinueToUse();
    }
}
