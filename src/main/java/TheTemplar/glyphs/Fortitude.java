package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.powers.BulwarkPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Fortitude extends AbstractGlyph {
    public static final String classID = Fortitude.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int EVOKE_SINGLE = 3;
    private static final int EVOKE_DOUBLE = 8;

    public Fortitude() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void evokeSingle() {
        doEffect(EVOKE_SINGLE);
    }

    @Override
    public void evokeDouble() {
        doEffect(EVOKE_DOUBLE);
    }

    private void doEffect(int amt) {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new GainBulwarkAction(amt));
    }
}
