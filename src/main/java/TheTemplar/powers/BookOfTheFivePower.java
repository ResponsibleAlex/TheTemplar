package TheTemplar.powers;

import TheTemplar.cards.BookOfTheFive;
import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class BookOfTheFivePower extends HolyWeaponPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(BookOfTheFivePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("BookOfTheFive84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("BookOfTheFive32.png"));

    private int maxAmount;

    public BookOfTheFivePower(boolean isUpgraded) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        maxAmount = amount = isUpgraded ? BookOfTheFive.TIMES + BookOfTheFive.UPGRADE_PLUS_TIMES : BookOfTheFive.TIMES;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void trigger() {
        flash();
        if (amount > 0) {
            amount--;
        }
        updateDescription();
    }

    @Override
    public void refresh(boolean isUpgraded) {
        upgraded = isUpgraded || upgraded;
        maxAmount = upgraded ? BookOfTheFive.TIMES + BookOfTheFive.UPGRADE_PLUS_TIMES : BookOfTheFive.TIMES;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount = maxAmount;
        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + maxAmount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BookOfTheFivePower(upgraded);
    }
}
