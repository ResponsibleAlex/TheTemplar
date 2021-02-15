package TheTemplar.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
public class AbstractMonsterPatch {
    public static final SpireField<Boolean> maxHpBuff = new SpireField<>(() -> false);
}
