package TheTemplar.potions;

import TheTemplar.actions.GlyphChoiceAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import TheTemplar.TemplarMod;

public class PotionOfLore extends CustomPotion {

    public static final String POTION_ID = TemplarMod.makeID("PotionOfLore");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String INSCRIBE_KEYWORD = TemplarMod.getModID().toLowerCase() + ":Inscribe";
    public static final String INSCRIBE_NAME = BaseMod.getKeywordProper(INSCRIBE_KEYWORD);
    public static final String INSCRIBE_DESCRIPTION = BaseMod.getKeywordDescription(INSCRIBE_KEYWORD);

    private static final int POTENCY = 1;

    public PotionOfLore() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.S, PotionColor.WHITE);
        isThrown = false;
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
        tips.add(new PowerTip(INSCRIBE_NAME, INSCRIBE_DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new GlyphChoiceAction(potency));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new PotionOfLore();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }

}
