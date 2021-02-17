package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SuppressWarnings("unused")
@SpirePatch(clz = DamageAllEnemiesAction.class, method = SpirePatch.CLASS)
public class DamageAllEnemiesActionPatch {
    // If we have any Holy Weapon equipped, hijack any existing AttackEffect and use
    // the appropriate custom one. If the attack effect is NONE, assume the card is using
    // a special effect and don't trigger ours.
    @SpirePatch(
            clz = DamageAllEnemiesAction.class,
            method = "update"
    )
    public static class DamageAllEnemiesActionUpdate {
        public static void Prefix(DamageAllEnemiesAction __instance, boolean ___firstFrame) {
            if (___firstFrame &&
                    TemplarMod.shouldUseCustomAttackEffect() &&
                    __instance.damageType == DamageInfo.DamageType.NORMAL &&
                    __instance.attackEffect != AbstractGameAction.AttackEffect.NONE) {

                __instance.attackEffect = AbstractGameAction.AttackEffect.NONE;
                TemplarMod.flashCustomAttackAllEffect();
            }
        }
    }
}
