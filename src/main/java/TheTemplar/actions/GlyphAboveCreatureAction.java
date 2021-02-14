package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.vfx.GlyphAboveCreatureEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GlyphAboveCreatureAction extends AbstractGameAction {
    private boolean used = false;
    private final AbstractGlyph glyph;

    public GlyphAboveCreatureAction(AbstractCreature target, AbstractGlyph glyph) {
        setValues(target, AbstractDungeon.player);
        this.glyph = glyph;
        actionType = ActionType.TEXT;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (!used) {
            AbstractDungeon.effectList.add(new GlyphAboveCreatureEffect(target, glyph));
            used = true;
        }

        tickDuration();
    }
}
