package TheTemplar.powers;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayDeque;
import java.util.Deque;

import static TheTemplar.TemplarMod.makePowerPath;

public class AltarPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(AltarPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Altar84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Altar32.png"));

    public AltarPower(final int amount) {
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
    public void atEndOfTurn(boolean isPlayer) {
        if (AbstractGlyph.canMatchAltar()) {
            flash();

            Deque<AbstractGlyph> glyphs = new ArrayDeque<>();
            for (int i = 0; i < amount; i++) {
                glyphs.push(AbstractGlyph.getCopyOfLeftGlyph());
            }

            while (!glyphs.isEmpty()) {
                addToTop(new GlyphInscribeAction(glyphs.pop()));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AltarPower(amount);
    }
}
