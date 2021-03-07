package TheTemplar.patches;

import TheTemplar.powers.SacredHammerPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {
    @SpirePatch(
            clz= AbstractCard.class,
            method="freeToPlay"
    )
    public static class FreeToPlay {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            if (SacredHammerPower.freeToPlay(__instance)) {
                return SpireReturn.Return(true);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
