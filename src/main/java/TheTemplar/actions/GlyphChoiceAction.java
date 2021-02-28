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
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (duration == startDuration) {
            ArrayList<AbstractCard> glyphChoices = new ArrayList<>();
            glyphChoices.add(new InscribeJustice(number));
            glyphChoices.add(new InscribeFortitude(number));
            glyphChoices.add(new InscribeValor(number));
            glyphChoices.add(new InscribeZeal(number));
            glyphChoices.add(new InscribeCharity(number));

            addToBot(new ChooseOneAction(glyphChoices));
        }

        tickDuration();
    }
}
