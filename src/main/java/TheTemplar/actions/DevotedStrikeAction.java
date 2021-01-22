package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DevotedStrikeAction extends AbstractGameAction {
    public DevotedStrikeAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            ArrayList<AbstractCard> eligibleCards = new ArrayList<>();

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    eligibleCards.add(c);
                }
            }

            for (CardQueueItem cqi : AbstractDungeon.actionManager.cardQueue) {
                if (cqi.card != null) {
                    eligibleCards.remove(cqi.card);
                }
            }

            if (!eligibleCards.isEmpty()) {
                AbstractCard c = eligibleCards.get(AbstractDungeon.cardRandomRng.random(0, eligibleCards.size() - 1));
                c.setCostForTurn(0);
                c.superFlash();
            }
        }

        this.tickDuration();
    }
}
