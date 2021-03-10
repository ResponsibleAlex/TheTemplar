package TheTemplar.powers;

import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.cards.Aegis;
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

public class AegisPower extends HolyWeaponPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(AegisPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Aegis84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Aegis32.png"));

    public AegisPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        addToBot(new GainBulwarkAction(amount));
    }

    @Override
    public void refresh(boolean isUpgraded) {
        upgraded = isUpgraded || upgraded;
        amount = upgraded ? Aegis.UPGRADED_BULWARK : Aegis.BULWARK;
        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AegisPower(upgraded);
    }
}
