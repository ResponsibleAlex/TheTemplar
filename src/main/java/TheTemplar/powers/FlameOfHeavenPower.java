package TheTemplar.powers;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Valor;
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

public class FlameOfHeavenPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(FlameOfHeavenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("FlameOfHeaven84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("FlameOfHeaven32.png"));

    public final boolean upgraded;

    public FlameOfHeavenPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = 0;
        this.upgraded = upgraded;

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new GlyphInscribeAction(new Valor()));
    }

    @Override
    public void updateDescription() {
        if (this.upgraded) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FlameOfHeavenPower(this.upgraded);
    }
}
