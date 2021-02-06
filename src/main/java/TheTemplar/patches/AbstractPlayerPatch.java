package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="onVictory"
    )
    public static class OnVictory {
        public static void Prefix(AbstractPlayer __instance) { TemplarMod.resetTemplarState(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="update"
    )
    public static class Update {
        public static void Prefix(AbstractPlayer __instance) { TemplarMod.updateGlyphsAnimation(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="combatUpdate"
    )
    public static class CombatUpdate {
        public static void Prefix(AbstractPlayer __instance) { TemplarMod.combatUpdate(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class PreBattlePrep {
        public static void Prefix(AbstractPlayer __instance)
        {
            TemplarMod.resetTemplarState();
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="render"
    )
    public static class RenderGlyphs
    {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            TemplarMod.renderGlyphs(sb);
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="useCard"
    )
    public static class UseCard
    {
        public static void Prefix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            __instance.cardInUse = c;
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="applyStartOfTurnRelics"
    )
    public static class ApplyStartOfTurnRelics
    {
        public static void Prefix(AbstractPlayer __instance) {
            TemplarMod.startOfTurn();
        }
    }
}
