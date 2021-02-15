package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;
import java.util.stream.Collectors;

public class DevotedStrikeAction extends AbstractGameAction {
    public DevotedStrikeAction() {
        duration = startDuration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (duration == startDuration) {
            List<AbstractCard> eligibleCards = AbstractDungeon.player.hand.group.stream()
                                                                                .filter(c -> c.cost > 0)
                                                                                .filter(c -> c.costForTurn > 0)
                                                                                .filter(c -> !c.freeToPlayOnce)
                                                                                .collect(Collectors.toList());

            AbstractDungeon.actionManager.cardQueue.stream()
                                                   .filter(cqi -> cqi.card != null)
                                                   .map(cqi -> cqi.card)
                                                   .forEach(eligibleCards::remove);

            if (!eligibleCards.isEmpty()) {
                AbstractCard c = eligibleCards.get(AbstractDungeon.cardRandomRng.random(0, eligibleCards.size() - 1));
                c.setCostForTurn(0);
                c.superFlash();
            }
        }

        tickDuration();
    }
}
