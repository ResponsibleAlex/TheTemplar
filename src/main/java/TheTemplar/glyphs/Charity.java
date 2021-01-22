package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Charity extends AbstractGlyph {
    public static final String classID = Charity.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int EVOKE_SINGLE = 4;
    private static final int EVOKE_DOUBLE = 8;

    public Charity() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void evokeSingle() {
        //this.doEffect(EVOKE_SINGLE);
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new GainBlockAction(p, EVOKE_SINGLE));
    }

    @Override
    public void evokeDouble() {
        //this.doEffect(EVOKE_DOUBLE);
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new GainBlockAction(p, EVOKE_DOUBLE));
        this.addToBot(new RemoveDebuffsAction(p));
    }

    /*
    private void doEffect(int amt) {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amt), amt));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, amt), amt));
    }
    */
}
