package TheTemplar.powers;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Charity;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class CleansingRitePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(CleansingRitePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CleansingRite84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CleansingRite32.png"));

    private final AbstractPlayer p;

    public CleansingRitePower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        owner = p = AbstractDungeon.player;
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
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        AbstractCard c;

        for (int i = 0; i < amount; i++) {
            if (!p.hand.isEmpty()) {
                c = p.hand.getRandomCard(AbstractCard.CardType.CURSE, true);
                if (c == null) {
                    c = p.hand.getRandomCard(AbstractCard.CardType.STATUS, true);
                }

                if (c != null) {
                    flash();
                    p.hand.moveToExhaustPile(c);
                    addToBot(new GlyphInscribeAction(new Charity()));
                }
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
        return new CleansingRitePower(amount);
    }
}
