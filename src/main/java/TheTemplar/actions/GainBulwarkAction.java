package TheTemplar.actions;

import TheTemplar.powers.BulwarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainBulwarkAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public GainBulwarkAction(int amount) {
        this.amount = amount;
        actionType = ActionType.POWER;
        player = AbstractDungeon.player;
    }

    public void update() {
        addToBot(new ApplyPowerAction(player, player, new BulwarkPower(player, player, amount), amount));
        isDone = true;
    }
}
