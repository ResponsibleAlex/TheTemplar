package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GlyphInscribeRandomAction extends AbstractGameAction {
    private final AbstractGlyph glyphToInscribe;

    public GlyphInscribeRandomAction() {
        glyphToInscribe = AbstractGlyph.getRandomGlyph(true);
    }

    @Override
    public void update() {
        addToBot(new GlyphInscribeAction(glyphToInscribe));
        isDone = true;
    }
}
