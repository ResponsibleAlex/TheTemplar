package TheTemplar.patches;

import TheTemplar.powers.SacredHammerPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
public class AbstractMonsterPatch {
    public static SpireField<Boolean> maxHpBuff = new SpireField<>(() -> false);
}
