package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CleanseAction extends AbstractGameAction {
    public CleanseAction() {
        this.actionType = ActionType.EXHAUST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractCard c;

        if (!p.hand.isEmpty()) {

            c = p.hand.getRandomCard(AbstractCard.CardType.CURSE, true);
            if (c == null) {
                c = p.hand.getRandomCard(AbstractCard.CardType.STATUS, true);
                if (c == null) {
                    c = p.hand.getRandomCard(true, AbstractCard.CardRarity.BASIC);
                    if (c == null) {
                        c = p.hand.getRandomCard(true);
                    }
                }
            }

            p.hand.moveToExhaustPile(c);
        }

        this.isDone = true;
    }
}
