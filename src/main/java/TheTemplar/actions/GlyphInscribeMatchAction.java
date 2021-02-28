package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GlyphInscribeMatchAction extends AbstractGameAction {
    public GlyphInscribeMatchAction () {
    }

    @Override
    public void update() {
        if (AbstractGlyph.canMatch()) {
            addToBot(new GlyphInscribeAction(AbstractGlyph.getCopyOfLeftGlyph()));
        }

        isDone = true;
    }
}
