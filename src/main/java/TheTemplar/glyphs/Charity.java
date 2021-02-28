package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;

public class Charity extends AbstractGlyph {
    public static final String classID = Charity.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 5;

    public Charity() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        addToTop(new GlyphAboveCreatureAction(p, this));
        addToTop(new GainBlockAction(p, TRIGGER));
    }

    @Override
    public void triggerMatchBonus() {
        addToTop(new RemoveDebuffsAction(p));
    }

    @Override
    public AbstractGlyph makeCopy() {
        return new Charity();
    }
}
