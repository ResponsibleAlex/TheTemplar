package TheTemplar.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class StalwartPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(StalwartPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Stalwart84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Stalwart32.png"));

    public StalwartPower(final int numberOfTurns) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        amount = numberOfTurns;
        if (amount >= 999) {
            amount = 999;
        }

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount >= 999) {
            amount = 999;
        }
    }

    public void reduce() {
        amount--;
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new StalwartPower(amount);
    }
}
