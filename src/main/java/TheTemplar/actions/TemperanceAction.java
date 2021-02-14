package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class TemperanceAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final int amt;

    public TemperanceAction(AbstractPlayer player, int block) {
        this.player = player;
        amt = block;
    }

    @Override
    public void update() {
        boolean hasAttacks = player.hand.group.stream()
                                              .anyMatch(c -> c.type == AbstractCard.CardType.ATTACK);

        if (!hasAttacks) {
            addToBot(new GainBlockAction(player, player, amt));
        }

        isDone = true;
    }
}
