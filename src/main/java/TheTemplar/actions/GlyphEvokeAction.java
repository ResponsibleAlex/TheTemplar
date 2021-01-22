package TheTemplar.actions;

import TheTemplar.glyphs.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GlyphEvokeAction extends AbstractGameAction {

    public GlyphEvokeAction() {
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractGlyph.evokeGlyphs();
        this.addToBot(new GlyphRemoveAction());

        this.isDone = true;
    }

}
