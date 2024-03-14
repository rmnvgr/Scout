package pm.c7.scout.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.c7.scout.item.BaseBagItem;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    // Trinkets calls areEqual to check whether it should unequip old and equip new (https://github.com/emilyploszaj/trinkets/blob/7cb63ce0/src/main/java/dev/emi/trinkets/mixin/LivingEntityMixin.java#L155-L158)
    // Excluding ourselves from this check to force unequip/equip when switching bag items fixes a duplication bug
    // Gross and hacky but oh well, can't mixin mixins.
    @Inject(method = "areEqual", at = @At("HEAD"), cancellable = true)
    private static void scout$grossTrinketsEquipFix(ItemStack stack, ItemStack otherStack, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (stack.getItem() instanceof BaseBagItem && otherStack.getItem() instanceof BaseBagItem) {
            callbackInfo.setReturnValue(false);
        }
    }
}
