package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GlyphRemoveAction extends AbstractGameAction {
    public GlyphRemoveAction() {
        actionType = ActionType.SPECIAL;
        duration = AbstractGlyph.FADE_TIME;
    }

    @Override
    public void update() {
        if (duration == AbstractGlyph.FADE_TIME) {
            AbstractGlyph.fadeGlyphsAnimation();
        }

        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0.0F) {
            AbstractGlyph.removeGlyphs();
            isDone = true;
        }
    }
}
