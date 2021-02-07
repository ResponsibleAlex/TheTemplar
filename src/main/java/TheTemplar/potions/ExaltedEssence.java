package TheTemplar.potions;

import TheTemplar.TemplarMod;
import TheTemplar.actions.ExaltedEssenceAction;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ExaltedEssence extends CustomPotion {

    public static final String POTION_ID = TemplarMod.makeID("ExaltedEssence");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final int POTENCY = 1;

    public ExaltedEssence() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.WHITE);
        this.isThrown = false;
    }

    public void initializeData() {
        potency = getPotency();
        if (null != AbstractDungeon.player && AbstractDungeon.player.hasRelic(SacredBark.ID)) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ExaltedEssenceAction(this.potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ExaltedEssence();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }

}
