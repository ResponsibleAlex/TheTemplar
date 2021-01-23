package TheTemplar.patches;

import TheTemplar.cards.BladesOfTheFallen;
import TheTemplar.powers.SacredHammerPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
public class AbstractCreaturePatch {
    @SpirePatch(
            clz= AbstractCreature.class,
            method="decrementBlock"
    )
    public static class DecrementBlock {
        public static SpireReturn<Integer> Prefix(AbstractCreature __instance, DamageInfo info, int damageAmount) {
            if (info.type == DamageInfo.DamageType.NORMAL && info.owner.isPlayer) {

                if (AbstractDungeon.player.hasPower(SacredHammerPower.POWER_ID)) {
                    return SpireReturn.Return(damageAmount);
                }

                if (AbstractDungeon.player.cardInUse != null) {
                    if (AbstractDungeon.player.cardInUse.cardID.equals(BladesOfTheFallen.ID)) {
                        return SpireReturn.Return(damageAmount);
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }
}
