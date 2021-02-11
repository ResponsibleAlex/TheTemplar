package TheTemplar.actions;

import TheTemplar.glyphs.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GlyphTriggerAction extends AbstractGameAction {

    public GlyphTriggerAction() {
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractGlyph.triggerGlyphs();
        this.addToTop(new GlyphRemoveAction());

        this.isDone = true;
    }

}
