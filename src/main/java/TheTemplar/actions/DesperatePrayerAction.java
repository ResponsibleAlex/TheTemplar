package TheTemplar.actions;

import TheTemplar.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DesperatePrayerAction extends AbstractGameAction {
    private final boolean setCost;
    private final AbstractPlayer p;
    private final String text;

    public DesperatePrayerAction(boolean upgraded, String chooseACardText) {
        this.text = chooseACardText;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.setCost = upgraded;
    }

    public void update() {
        if (duration == startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (1 == p.hand.group.size()) {
                // only 1 card
                AbstractCard c = this.p.hand.getTopCard();
                this.setDesperatePrayer(c);
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                tickDuration();
            }
        } else {

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard chosen = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
                this.setDesperatePrayer(chosen);

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

                ArrayList<AbstractCard> others = new ArrayList<>();
                for (AbstractCard c : this.p.hand.group) {
                    if (c.uuid != chosen.uuid) {
                        others.add(c);
                    }
                }
                for (AbstractCard c : others) {
                    this.p.hand.moveToDiscardPile(c);
                }

                this.p.hand.refreshHandLayout();
            }

            tickDuration();
        }
    }

    private void setDesperatePrayer(AbstractCard c) {
        if (c.getClass().isInstance(AbstractDynamicCard.class)) {
            ((AbstractDynamicCard)c).triggerNextBlessing = true;
        }

        if (this.setCost) {
            c.setCostForTurn(0);
        }

        c.superFlash();
    }
}
