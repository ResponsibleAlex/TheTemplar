package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.powers.AltarPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayDeque;
import java.util.Deque;

public class AltarAction extends AbstractGameAction {
    private final AltarPower power;

    public AltarAction(AltarPower altarPower) {
        power = altarPower;
        amount = altarPower.amount;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (AbstractGlyph.canMatchAltar()) {
            power.flash();

            Deque<AbstractGlyph> glyphs = new ArrayDeque<>();
            for (int i = 0; i < amount; i++) {
                glyphs.push(AbstractGlyph.getCopyOfLeftGlyph());
            }

            while (!glyphs.isEmpty()) {
                addToBot(new GlyphInscribeAction(glyphs.pop()));
            }
        }

        isDone = true;
    }
}
