package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class GildedEgg extends CustomRelic {
    public static final String ID = TemplarMod.makeID("GildedEgg");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GildedEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GildedEgg.png"));

    public GildedEgg() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
        this.counter = 2;
    }

    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null) {
                for (AbstractCard c : reward.cards) {
                    this.onPreviewObtainCard(c);
                }
            }
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        if (this.counter > 0 && c.rarity == AbstractCard.CardRarity.RARE && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (this.counter > 0 && c.rarity == AbstractCard.CardRarity.RARE && c.canUpgrade() && !c.upgraded) {
            this.flash();
            c.upgrade();
            this.decrementCounter();
        }
    }

    public void decrementCounter() {
        this.counter--;
        if (this.counter == 0) {
            this.usedUp();
            this.counter = -2;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GildedEgg();
    }
}
