package TheTemplar.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class BottledQuicksilverPatch {
    public static SpireField<Boolean> inBottledQuicksilver = new SpireField<>(() -> false);

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            inBottledQuicksilver.set(result, inBottledQuicksilver.get(self));
            return result;
        }
    }

}