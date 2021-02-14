package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExaltedEssenceAction extends AbstractGameAction {
    public ExaltedEssenceAction(int amt) {
        amount = amt;
        actionType = ActionType.SPECIAL;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (duration == startDuration) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hand.size() < 5) {
                int amtToDraw = 5 - player.hand.size();
                addToBot(new DrawCardAction(amtToDraw));
            }
            addToBot(new GainEnergyAction(amount));
        }

        tickDuration();
    }
}
