package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Valor extends AbstractGlyph {
    public static final String classID = Valor.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int EVOKE_SINGLE = 5;
    private static final int EVOKE_DOUBLE = 8;
    private static final int EVOKE_STR = 2;

    public Valor() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void evokeSingle() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, EVOKE_SINGLE), EVOKE_SINGLE, true));
    }

    @Override
    public void evokeDouble() {
        this.addToBot(new GlyphAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, EVOKE_DOUBLE), EVOKE_DOUBLE, false));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, EVOKE_STR), EVOKE_STR));
    }
}
