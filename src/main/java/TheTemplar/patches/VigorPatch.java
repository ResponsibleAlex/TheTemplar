package TheTemplar.patches;

import TheTemplar.cards.Momentum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

@SuppressWarnings({"unused", "rawtypes"})
@SpirePatch(clz = VigorPower.class, method = SpirePatch.CLASS)
public class VigorPatch {
    @SpirePatch(
            clz= VigorPower.class,
            method="onUseCard"
    )
    public static class OnUseCard {
        public static SpireReturn Prefix(VigorPower __instance, AbstractCard card, UseCardAction action) {
            if (card.cardID.equals(Momentum.ID)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
