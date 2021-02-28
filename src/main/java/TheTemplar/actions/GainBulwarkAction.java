package TheTemplar.actions;

import TheTemplar.powers.BulwarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainBulwarkAction extends AbstractGameAction {

    private final AbstractPlayer p;
    public GainBulwarkAction(int amount) {
        this.amount = amount;
        actionType = ActionType.POWER;
        p = AbstractDungeon.player;
    }

    public void update() {
        addToBot(new ApplyPowerAction(p, p, new BulwarkPower(p, p, amount), amount));
        isDone = true;
    }
}
