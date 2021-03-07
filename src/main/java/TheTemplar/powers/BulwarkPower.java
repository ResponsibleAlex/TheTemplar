package TheTemplar.powers;

import TheTemplar.cards.Aegis;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class BulwarkPower extends AbstractPower implements CloneablePowerInterface {
    public final AbstractCreature source;
    public final AbstractPlayer p;

    public static final String POWER_ID = TemplarMod.makeID("BulwarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Bulwark84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Bulwark32.png"));

    public BulwarkPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.source = source;
        p = AbstractDungeon.player;

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
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new GainBlockAction(p, p, amount));

        float reduction = 0.5f;

        if (owner.hasPower(AegisPower.POWER_ID)) {
            // we have Aegis, set reduction and deal damage
            AegisPower aegisPower = (AegisPower) owner.getPower(AegisPower.POWER_ID);

            addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, aegisPower.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

            if (aegisPower.upgraded) {
                reduction = Aegis.UPGRADE_REDUCTION / 100f;
            } else {
                reduction = Aegis.REDUCTION / 100f;
            }
        }

        if (owner.hasPower(StalwartPower.POWER_ID)) {
            // we have Stalwart, reduce that by 1 and remove if 0
            StalwartPower stalwart = (StalwartPower) owner.getPower(StalwartPower.POWER_ID);
            stalwart.reduce();
        } else {
            // we do not have Stalwart, reduce Bulwark
            amount *= reduction;
            if (amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, ID));
            } else {
                updateDescription();
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BulwarkPower(owner, source, amount);
    }
}
