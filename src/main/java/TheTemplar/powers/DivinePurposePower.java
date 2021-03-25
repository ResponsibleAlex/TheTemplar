package TheTemplar.powers;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Zeal;
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

public class DivinePurposePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(DivinePurposePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DivinePurpose84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DivinePurpose32.png"));

    public DivinePurposePower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
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

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for (int i = 0; i < amount; i++) {
            addToBot(new GlyphInscribeAction(new Zeal()));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DivinePurposePower(amount);
    }
}
