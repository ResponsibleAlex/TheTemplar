package TheTemplar.actions;

import TheTemplar.TemplarMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.stream.IntStream;

public class LionheartAction extends AbstractGameAction {

    private final AbstractCard card;

    public LionheartAction(AbstractCard card) {
        this.card = card;
        actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (!isDone) {
            IntStream.range(0, TemplarMod.glyphsInscribedThisCombat)
                     .mapToObj(i -> new LionheartAttackAction(card))
                     .forEach(this::addToBot);

            isDone = true;
        }
    }
}
