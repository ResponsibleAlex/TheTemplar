package TheTemplar.actions;

import TheTemplar.TemplarMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class LionheartAction extends AbstractGameAction {

    private final AbstractCard card;

    public LionheartAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (!this.isDone) {
            for (int i = 0; i < TemplarMod.glyphsInscribedThisCombat; i++) {
                this.addToBot(new LionheartAttackAction(this.card));
            }

            this.isDone = true;
        }
    }
}
