package TheTemplar.powers;

import TheTemplar.cards.FireOfConviction;
import TheTemplar.cards.FlameOfHeaven;
import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class FlameOfHeavenPower extends HolyWeaponPower implements CloneablePowerInterface {

    private final int magicNumber;

    public static final String POWER_ID = TemplarMod.makeID(FlameOfHeavenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("FlameOfHeaven84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("FlameOfHeaven32.png"));

    public FlameOfHeavenPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        amount = 5;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
        if (this.upgraded) {
            magicNumber = FlameOfHeaven.UPGRADED_BLOCK;
        } else {
            magicNumber = FlameOfHeaven.BLOCK;
        }
        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        --amount;
        if (0 == amount) {
            flash();
            amount = 5;

            addToBot(new GainBlockAction(owner, owner, magicNumber));
            addToBot(new MakeTempCardInHandAction(new FireOfConviction(), 1, false));
        }

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount = 5;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (1 == amount) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] +
                    magicNumber + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] +
                    magicNumber + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FlameOfHeavenPower(upgraded);
    }
}
