package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.relics.RunedArmor;
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
        addToTop(new GlyphAboveCreatureAction(p, this));
        addToTop(new DrawCardAction(TRIGGER));
    }

    @Override
    public void triggerMatchBonus() {
        int amt = MATCH_BONUS;
        if (p.hasRelic(RunedArmor.ID)) {
            amt *= 2;
        }
        addToTop(new GainEnergyAction(amt));
    }

    @Override
    public AbstractGlyph makeCopy() {
        return new Zeal();
    }
}
