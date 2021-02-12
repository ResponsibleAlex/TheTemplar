package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SuppressWarnings("unused")
@SpirePatch(clz = DamageAction.class, method = SpirePatch.CLASS)
public class DamageActionPatch {

    // If we have any Holy Weapon equipped, hijack any existing AttackEffect and use
    // the appropriate custom one. If the attack effect is NONE, assume the card is using
    // a special effect and don't trigger ours.
    @SpirePatch(
            clz = DamageAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCreature.class,
                    DamageInfo.class,
                    AbstractGameAction.AttackEffect.class
            }
    )
    public static class DamageActionConstructor {
        public static void Postfix(DamageAction __instance, AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
            if (info.owner.isPlayer) {
                if (effect != AbstractGameAction.AttackEffect.NONE
                        && info.type == DamageInfo.DamageType.NORMAL
                        && TemplarMod.shouldUseCustomAttackEffect()) {

                    __instance.attackEffect = AbstractGameAction.AttackEffect.NONE;
                    TemplarMod.flashCustomAttackEffect(target);
                }
            }
        }
    }
}
