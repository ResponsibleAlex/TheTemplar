package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CLASS)
public class AbstractDungeonPatch {
    @SpirePatch(
            clz= AbstractDungeon.class,
            method="resetPlayer"
    )
    public static class ResetPlayer {
        public static void Postfix() { TemplarMod.resetTemplarState(); }
    }
}
