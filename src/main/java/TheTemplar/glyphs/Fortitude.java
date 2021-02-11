package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.powers.ResoluteWillPower;
import TheTemplar.relics.RunedArmor;
import basemod.BaseMod;

public class Fortitude extends AbstractGlyph {
    public static final String classID = Fortitude.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 3;
    private static final int MATCH_BONUS = 3;

    public Fortitude() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        int amt = TRIGGER;
        if (p.hasPower(ResoluteWillPower.POWER_ID)) {
            amt += p.getPower(ResoluteWillPower.POWER_ID).amount;
        }
        this.addToTop(new GainBulwarkAction(amt));
    }

    @Override
    public void triggerMatchBonus() {
        int amt = MATCH_BONUS;
        if (p.hasRelic(RunedArmor.ID)) {
            amt *= 2;
        }
        this.addToTop(new GainBulwarkAction(amt));
    }

    @Override
    public AbstractGlyph makeCopy() {
        return new Fortitude();
    }
}
