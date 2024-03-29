package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.relics.RunedArmor;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Valor extends AbstractGlyph {
    public static final String classID = Valor.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 5;
    private static final int MATCH_BONUS = 2;

    public Valor() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        addToBot(new GlyphAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, TRIGGER), TRIGGER, true));
    }

    @Override
    public void triggerMatchBonus() {
        int amt = MATCH_BONUS;
        if (p.hasRelic(RunedArmor.ID)) {
            p.getRelic(RunedArmor.ID).flash();
            amt *= 2;
        }
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, amt), amt, true));
    }

    @Override
    public AbstractGlyph makeCopy() {
        return new Valor();
    }
}
