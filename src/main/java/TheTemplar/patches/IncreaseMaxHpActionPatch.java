package TheTemplar.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("unused")
@SpirePatch(clz = IncreaseMaxHpAction.class, method = SpirePatch.CLASS)
public class IncreaseMaxHpActionPatch {
    @SpirePatch(
            clz= IncreaseMaxHpAction.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class IncreaseMaxHpActionConstructor {
        public static void Prefix(IncreaseMaxHpAction __instance, AbstractMonster m, float increasePercent, boolean showEffect) {
            AbstractMonsterPatch.maxHpBuff.set(m, true);
        }
    }
}
