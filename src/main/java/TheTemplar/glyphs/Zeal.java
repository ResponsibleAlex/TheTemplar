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

    private static final int TRIGGER = 1;
    private static final int MATCH_BONUS = 1;

    public Zeal() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new DrawCardAction(TRIGGER));
    }

    @Override
    public void triggerMatchBonus() {
        this.addToBot(new GainEnergyAction(MATCH_BONUS));
    }
}
