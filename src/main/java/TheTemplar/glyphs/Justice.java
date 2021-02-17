package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.JusticeAction;
import TheTemplar.powers.ResoluteWillPower;
import TheTemplar.relics.RunedArmor;
import basemod.BaseMod;

public class Justice extends AbstractGlyph {
    public static final String classID = Justice.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 3;
    private static final int MATCH_BONUS = 6;

    public Justice() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        int amt = TRIGGER;
        if (p.hasPower(ResoluteWillPower.POWER_ID)) {
            amt += p.getPower(ResoluteWillPower.POWER_ID).amount;
        }
        this.addToTop(new JusticeAction(amt, false, this));
    }

    @Override
    public void triggerMatchBonus() {
        int amt = MATCH_BONUS;
        if (p.hasRelic(RunedArmor.ID)) {
            p.getRelic(RunedArmor.ID).flash();
            amt *= 2;
        }
        this.addToTop(new JusticeAction(amt, true, this));
    }

    @Override
    public AbstractGlyph makeCopy() {
        return new Justice();
    }
}
