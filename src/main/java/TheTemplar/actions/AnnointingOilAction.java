package TheTemplar.actions;

import TheTemplar.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class AnnointingOilAction extends AbstractGameAction {
    private boolean wasCardRetrieved = false;
    private final boolean shouldUpgrade;

    public AnnointingOilAction(int potency) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        shouldUpgrade = (potency == 2) || AbstractDungeon.player.hasPower("MasterRealityPower");
    }

    public void update() {

        if (duration == startDuration) {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            cards.add(new Aegis());
            cards.add(new BookOfTheFive());
            cards.add(new FlameOfHeaven());
            cards.add(new KingSword());
            cards.add(new SacredHammer());
            if (shouldUpgrade) {
                for (AbstractCard c : cards) {
                    c.upgrade();
                }
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], false);

        } else {
            if (!wasCardRetrieved) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    wasCardRetrieved = true;

                    AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    card.setCostForTurn(0);
                    card.current_x = -1000.0F * Settings.xScale;

                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
            }
        }

        tickDuration();
    }
}
