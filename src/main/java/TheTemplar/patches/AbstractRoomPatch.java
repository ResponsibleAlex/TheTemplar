package TheTemplar.patches;

import TheTemplar.TemplarMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractRoom.class, method = SpirePatch.CLASS)
public class AbstractRoomPatch {
    @SpirePatch(
            clz= AbstractRoom.class,
            method="applyEndOfTurnPreCardPowers"
    )
    public static class ApplyEndOfTurnPreCardPowers
    {
        // make sure Altar triggers after Cleansing Rite and Battle Tactics
        public static void Postfix(AbstractRoom __instance) {
            TemplarMod.triggerAltar();
        }
    }
}
