package TheTemplar.actions;

import TheTemplar.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DesperatePrayerAction extends AbstractGameAction {
    private final boolean isSetCost;
    private final AbstractPlayer player;
    private final String cardText;

    public DesperatePrayerAction(boolean isSetCost, String chooseACardText) {
        cardText = chooseACardText;
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.isSetCost = isSetCost;
    }

    public void update() {
        if (duration == startDuration) {
            if (player.hand.isEmpty()) {
                isDone = true;
            } else if (1 == player.hand.group.size()) {
                // only 1 card
                setDesperatePrayer(player.hand.getTopCard());
                isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(cardText, 1, false, false, false, false);
                tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard chosen = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
                setDesperatePrayer(chosen);

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

                player.hand.group.stream()
                                 .filter(c -> c.uuid != chosen.uuid)
                                 .forEach(c -> player.hand.moveToDiscardPile(c));

                player.hand.addToTop(chosen);
                player.hand.refreshHandLayout();
            }

            tickDuration();
        }
    }

    private void setDesperatePrayer(AbstractCard c) {
        if (c instanceof AbstractDynamicCard) {
            ((AbstractDynamicCard) c).triggerNextBlessing = true;
        }

        if (isSetCost) {
            c.setCostForTurn(0);
        }

        c.superFlash();
    }
}
