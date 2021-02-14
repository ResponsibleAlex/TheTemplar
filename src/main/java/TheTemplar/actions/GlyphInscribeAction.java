package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.cards.LightOfGlory;
import TheTemplar.glyphs.*;
import TheTemplar.powers.BookOfTheFivePower;
import TheTemplar.variables.GlyphTypes;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collection;

public class GlyphInscribeAction extends AbstractGameAction {
    private final AbstractGlyph glyphToInscribe;

    public GlyphInscribeAction(AbstractGlyph glyph) {
        this(glyph, true);
    }

    public GlyphInscribeAction(AbstractGlyph glyph, boolean canDupWithBook) {
        glyphToInscribe = glyph;

        actionType = ActionType.SPECIAL;
        duration = startDuration = AbstractGlyph.INSCRIBE_TIME;

        if (canDupWithBook && AbstractDungeon.player.hasPower(BookOfTheFivePower.POWER_ID)) {
            addToBot(new GlyphInscribeAction(glyph.makeCopy(), false));
        }
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            TemplarMod.glyphsInscribedThisCombat++;
            updateLightOfGlory();

            if (glyphToInscribe.name.equals(Valor.classID)) {
                TemplarMod.valorInscribedThisCombat++;
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Valor.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Fortitude.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Fortitude.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Justice.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Justice.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Zeal.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Zeal.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Charity.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Charity.ordinal()] = true;
            }

            if (AbstractGlyph.noneInscribed()) {
                AbstractGlyph.inscribeLeft(glyphToInscribe);
            } else {
                AbstractGlyph.inscribeRight(glyphToInscribe);
                addToTop(new GlyphTriggerAction());
            }
        }

        tickDuration();
    }

    private void updateLightOfGlory() {
        updateLightOfGloryPile(AbstractDungeon.player.hand.group);
        updateLightOfGloryPile(AbstractDungeon.player.exhaustPile.group);
        updateLightOfGloryPile(AbstractDungeon.player.drawPile.group);
        updateLightOfGloryPile(AbstractDungeon.player.discardPile.group);
    }

    private void updateLightOfGloryPile(Collection<AbstractCard> pile) {
        pile.stream()
            .filter(c -> c.cardID.equals(LightOfGlory.ID))
            .forEach(c -> c.updateCost(-1));
    }
}
