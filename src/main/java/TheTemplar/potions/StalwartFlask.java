package TheTemplar.potions;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.powers.StalwartPower;
import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class StalwartFlask extends CustomPotion {

    public static final String POTION_ID = TemplarMod.makeID("StalwartFlask");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String BULWARK_KEYWORD = TemplarMod.getModID().toLowerCase() + ":Bulwark";
    public static final String BULWARK_NAME = BaseMod.getKeywordProper(BULWARK_KEYWORD);
    public static final String BULWARK_DESCRIPTION = BaseMod.getKeywordDescription(BULWARK_KEYWORD);

    private static final int POTENCY = 8;

    public StalwartFlask() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData() {
        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BULWARK_NAME, BULWARK_DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new GainBulwarkAction(potency));

            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StalwartPower(1), 1));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new StalwartFlask();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }

}
