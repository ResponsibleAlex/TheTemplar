package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

@SuppressWarnings("unused")
@SpirePatch(clz = DamageAction.class, method = SpirePatch.CLASS)
public class DamageActionPatch {
    // If we have any Holy Weapon equipped, hijack any existing AttackEffect and use
    // the appropriate custom one. If the attack effect is NONE, assume the card is using
    // a special effect and don't trigger ours.
    @SpirePatch(
            clz = DamageAction.class,
            method = "update"
    )
    public static class DamageActionUpdate {
        public static void Prefix(DamageAction __instance, DamageInfo ___info, float ___duration) {
            if (___info.owner != null && ___info.owner.isPlayer && ___duration == 0.1F) {
                if (__instance.attackEffect != AbstractGameAction.AttackEffect.NONE
                        && ___info.type == DamageInfo.DamageType.NORMAL
                        && TemplarMod.shouldUseCustomAttackEffect()) {

                    __instance.attackEffect = AbstractGameAction.AttackEffect.NONE;
                    TemplarMod.flashCustomAttackEffect(__instance.target);
                }
            }
        }
    }
}
