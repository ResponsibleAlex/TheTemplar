package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.cards.HolyStrike;
import TheTemplar.cards.LightOfGlory;
import TheTemplar.glyphs.*;
import TheTemplar.powers.BookOfTheFivePower;
import TheTemplar.variables.GlyphTypes;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GlyphInscribeAction extends AbstractGameAction {
    private final AbstractGlyph glyphToInscribe;

    public GlyphInscribeAction(AbstractGlyph glyph) {
        this(glyph, true);
    }

    private GlyphInscribeAction(AbstractGlyph glyph, boolean canDupeWithBook) {
        glyphToInscribe = glyph;

        actionType = ActionType.SPECIAL;
        duration = startDuration = AbstractGlyph.INSCRIBE_TIME;

        if (canDupeWithBook && AbstractDungeon.player.hasPower(BookOfTheFivePower.POWER_ID)) {
            BookOfTheFivePower bookPower = (BookOfTheFivePower) AbstractDungeon.player.getPower(BookOfTheFivePower.POWER_ID);
            if (bookPower.amount > 0) {
                bookPower.trigger();
                addToBot(new GlyphInscribeAction(glyph.makeCopy(), false));
            }
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
                triggerHolyStrike();
            } else if (glyphToInscribe.name.equals(Fortitude.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Fortitude.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Justice.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Justice.ordinal()] = true;
            } else if (glyphToInscribe.name.equals(Zeal.classID)) {
                TemplarMod.glyphTypesInscribedThisCombat[GlyphTypes.Zeal.ordinal()] = true;
                triggerHolyStrike();
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

    private void triggerHolyStrike() {
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof HolyStrike) {
                addToBot(new DiscardToHandAction(c));
            }
        }
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
