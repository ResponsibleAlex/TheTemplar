package TheTemplar.powers;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Valor;
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

public class FlameOfHeavenPower extends HolyWeaponPower implements CloneablePowerInterface {
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
        amount = 0;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
    }

    public void stackPower(int unused) { }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new GlyphInscribeAction(new Valor()));
    }

    @Override
    public void updateDescription() {
        if (upgraded) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FlameOfHeavenPower(upgraded);
    }
}
