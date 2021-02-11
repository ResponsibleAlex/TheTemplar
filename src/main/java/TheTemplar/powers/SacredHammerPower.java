package TheTemplar.powers;

import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static TheTemplar.TemplarMod.makePowerPath;

public class SacredHammerPower extends HolyWeaponPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(SacredHammerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("SacredHammer84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("SacredHammer32.png"));

    public SacredHammerPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = 0;

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
    }

    public void stackPower(int unused) { }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target.currentBlock == 0) {
            this.flash();
            this.addToBot(new ApplyPowerAction(target, this.owner, new VulnerablePower(target, 1, false), 1));
            if (this.upgraded) {
                this.addToBot(new ApplyPowerAction(target, this.owner, new WeakPower(target, 1, false), 1));
            }
        }
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
        return new SacredHammerPower(this.upgraded);
    }
}
