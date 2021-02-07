package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExaltedEssenceAction extends AbstractGameAction {
    public ExaltedEssenceAction(int amt) {
        this.amount = amt;
        this.actionType = ActionType.SPECIAL;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hand.size() < 5) {
                int amtToDraw = 5 - p.hand.size();
                this.addToBot(new DrawCardAction(amtToDraw));
            }
            this.addToBot(new GainEnergyAction(this.amount));
        }

        this.tickDuration();
    }
}
