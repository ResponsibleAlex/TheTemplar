package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Valor extends AbstractGlyph {
    public static final String classID = Valor.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 4;
    private static final int MATCH_STR = 2;

    public Valor() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, TRIGGER), TRIGGER, true));
    }

    @Override
    public void triggerMatchBonus() {
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, MATCH_STR), MATCH_STR));
    }
}
