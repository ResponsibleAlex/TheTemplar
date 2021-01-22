package TheTemplar.patches;

import TheTemplar.powers.SacredHammerPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings({"unused"})
@SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
public class AbstractCreaturePatch {
    @SpirePatch(
            clz= AbstractCreature.class,
            method="decrementBlock"
    )
    public static class DecrementBlock {
        public static SpireReturn<Integer> Prefix(AbstractCreature __instance, DamageInfo info, int damageAmount) {
            if (AbstractDungeon.player.hasPower(SacredHammerPower.POWER_ID)
                    && info.type == DamageInfo.DamageType.NORMAL
                    && info.owner.isPlayer) {
                return SpireReturn.Return(damageAmount);
            }
            return SpireReturn.Continue();
        }
    }
}
