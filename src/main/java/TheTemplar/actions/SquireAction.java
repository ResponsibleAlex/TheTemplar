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
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.text = text;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!p.drawPile.isEmpty()) {

                if (p.drawPile.size() == 1) {
                    // only 1 card in draw pile, just copy it
                    this.addToTop(new MakeTempCardInHandAction(p.drawPile.getTopCard().makeStatEquivalentCopy()));

                    this.isDone = true;
                } else {
                    // choose from draw pile
                    CardGroup selection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : p.drawPile.group) {
                        selection.addToTop(c);
                    }

                    selection.sortAlphabetically(true);
                    selection.sortByRarityPlusStatusCardType(false);
                    AbstractDungeon.gridSelectScreen.open(selection, 1, text, false);

                    this.tickDuration();
                }
            } else {
                // draw pile was empty
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                // copy the chosen card
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                this.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
