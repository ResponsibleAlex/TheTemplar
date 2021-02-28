package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SquireAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final String text;

    public SquireAction(String text) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.text = text;
    }

    public void update() {
        if (duration == startDuration) {
            if (!p.drawPile.isEmpty()) {

                if (p.drawPile.size() == 1) {
                    // only 1 card in draw pile, just copy it
                    addToTop(new MakeTempCardInHandAction(p.drawPile.getTopCard().makeStatEquivalentCopy()));

                    isDone = true;
                } else {
                    // choose from draw pile
                    CardGroup selection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : p.drawPile.group) {
                        selection.addToTop(c);
                    }

                    selection.sortAlphabetically(true);
                    selection.sortByRarityPlusStatusCardType(false);
                    AbstractDungeon.gridSelectScreen.open(selection, 1, text, false);

                    tickDuration();
                }
            } else {
                // draw pile was empty
                isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                // copy the chosen card
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            tickDuration();
        }
    }
}
