package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.cards.LightOfGlory;
import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.glyphs.Valor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GlyphInscribeAction extends AbstractGameAction {
    private final AbstractGlyph glyphToInscribe;

    public GlyphInscribeAction(AbstractGlyph glyph) {
        glyphToInscribe = glyph;

        actionType = ActionType.SPECIAL;
        duration = startDuration = AbstractGlyph.INSCRIBE_TIME;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            TemplarMod.glyphsInscribedThisCombat++;
            updateLightOfGlory();

            if (glyphToInscribe.name.equals(Valor.classID)) {
                TemplarMod.valorInscribedThisCombat++;
            }

            if (AbstractGlyph.noneInscribed()) {
                AbstractGlyph.inscribeLeft(glyphToInscribe);
            } else {
                AbstractGlyph.inscribeRight(glyphToInscribe);
                this.addToBot(new GlyphEvokeAction());
            }
        }

        this.tickDuration();
    }

    private void updateLightOfGlory() {
        updateLightOfGloryPile(AbstractDungeon.player.hand.group);
        updateLightOfGloryPile(AbstractDungeon.player.exhaustPile.group);
        updateLightOfGloryPile(AbstractDungeon.player.drawPile.group);
        updateLightOfGloryPile(AbstractDungeon.player.discardPile.group);
    }

    private void updateLightOfGloryPile(ArrayList<AbstractCard> pile) {
        for (AbstractCard c: pile) {
            if (c.cardID.equals(LightOfGlory.ID)) {
                c.updateCost(-1);
            }
        }
    }
}
