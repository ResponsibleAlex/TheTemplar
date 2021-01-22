package TheTemplar.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

import static TheTemplar.TemplarMod.makePowerPath;

public class RingingChallengePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = TemplarMod.makeID(RingingChallengePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("RingingChallenge84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("RingingChallenge32.png"));

    private final int maxHp;
    private final boolean upgradeCard;

    public RingingChallengePower(int maxHp, boolean upgradeCard) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = 0;

        this.maxHp = maxHp;
        this.upgradeCard = upgradeCard;

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public void onVictory() {
        this.flash();

        if (this.maxHp > 0) {
            AbstractDungeon.player.increaseMaxHp(this.maxHp, true);
        }
        if (this.upgradeCard) {
            this.doUpgrade();
        }
    }

    private void doUpgrade() {
        CardGroup cards = AbstractDungeon.player.masterDeck.getUpgradableCards();
        ArrayList<AbstractCard> betterCards = new ArrayList<>();

        if (!cards.isEmpty()) {
            for (AbstractCard c : cards.group) {
                if (c.rarity != AbstractCard.CardRarity.BASIC) {
                    betterCards.add(c);
                }
            }

            AbstractCard c;
            if (betterCards.isEmpty()) {
                c = cards.getRandomCard(AbstractDungeon.miscRng);
            } else {
                c = betterCards.get(AbstractDungeon.miscRng.random(0, betterCards.size() - 1));
            }

            c.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(c);

            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RingingChallengePower(this.maxHp, this.upgradeCard);
    }
}
