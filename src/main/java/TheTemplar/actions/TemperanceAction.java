package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class TemperanceAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int amt;

    public TemperanceAction(AbstractPlayer player, int block) {
        p = player;
        amt = block;
    }

    @Override
    public void update() {
        boolean hasAttacks = false;

        for (AbstractCard c : p.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                hasAttacks = true;
                break;
            }
        }

        if (!hasAttacks) {
            this.addToBot(new GainBlockAction(p, p, amt));
        }

        this.isDone = true;
    }
}
