package TheTemplar.actions;

import TheTemplar.optionCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class GlyphChoiceAction extends AbstractGameAction {
    private final int number;

    public GlyphChoiceAction() {
        this(1);
    }

    public GlyphChoiceAction(int number) {
        this.number = number;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            ArrayList<AbstractCard> glyphChoices = new ArrayList<>();
            glyphChoices.add(new InscribeJustice(this.number));
            glyphChoices.add(new InscribeFortitude(this.number));
            glyphChoices.add(new InscribeValor(this.number));
            glyphChoices.add(new InscribeZeal(this.number));
            glyphChoices.add(new InscribeCharity(this.number));

            this.addToBot(new ChooseOneAction(glyphChoices));
        }

        this.tickDuration();
    }
}
