package net.turtlemaster42.pixelsofmc.entity.AI;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;

public class ShellRandomLookAroundGoal extends RandomLookAroundGoal {

    private final Mob mob;

    public ShellRandomLookAroundGoal(Mob pMob) {
        super(pMob);
        this.mob = pMob;
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
