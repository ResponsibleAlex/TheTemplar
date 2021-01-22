package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

public class Zeal extends AbstractGlyph {
    public static final String classID = Zeal.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int EVOKE_SINGLE = 1;
    private static final int EVOKE_DOUBLE = 2;
    private static final int EVOKE_ENERGY = 1;

    public Zeal() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void evokeSingle() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new DrawCardAction(EVOKE_SINGLE));
    }

    @Override
    public void evokeDouble() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new DrawCardAction(EVOKE_DOUBLE));
        this.addToBot(new GainEnergyAction(EVOKE_ENERGY));
    }
}
