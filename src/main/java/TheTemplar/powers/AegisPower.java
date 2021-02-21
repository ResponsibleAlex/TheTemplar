package TheTemplar.powers;

import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import java.util.HashMap;

import static TheTemplar.TemplarMod.makePowerPath;

public class AegisPower extends HolyWeaponPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(AegisPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Aegis84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Aegis32.png"));

    private final HashMap<Integer, Integer> dmgAmts = new HashMap<>();
    private final HashMap<Integer, Integer> specialCases = initSpecialCases();

    public AegisPower(final boolean upgraded) {
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
    public void atStartOfTurn() {
        dmgAmts.clear();
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            float newVal = damage * 0.75f; // the new value to be returned

            // Add this value to the lookup table for onAttacked since
            // AbstractMonster.calculateDamage is going to call Math.floor and
            // mess up our calculation. This will only work if our power is the
            // last one to modify the value.
            int newInt = (int)Math.floor(newVal);
            int difference = (int)damage - newInt;
            dmgAmts.put(newInt, difference);

            return newVal;
        } else {
            return damage;
        }
    }

    // If our saved dmgAmts fails, we account for some awkward rounding errors
    // since onAttacked calls Math.floor and we lose some information. These values
    // will give a little extra damage output to the player in some cases, but that's
    // preferable to short-changing the player.
    private HashMap<Integer, Integer> initSpecialCases() {
        HashMap<Integer, Integer> x = new HashMap<>();
        x.put(3, 2);
        x.put(6, 3);
        x.put(9, 4);
        x.put(13, 5);
        x.put(15, 6);
        x.put(16, 6);
        x.put(18, 7);
        x.put(19, 7);
        x.put(21, 8);
        x.put(22, 8);
        x.put(24, 9);
        x.put(25, 9);
        return x;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS
                && info.type != DamageInfo.DamageType.HP_LOSS
                && info.owner != this.owner
                && info.owner instanceof AbstractMonster) {

            // attempt to get the "real" value from the lookup table(s)
            int dmgBack;
            if (dmgAmts.containsKey(info.output)) {
                dmgBack = this.upgraded ? dmgAmts.get(info.output) * 2 : dmgAmts.get(info.output);
            } else if (specialCases.containsKey(info.output)) {
                dmgBack = this.upgraded ? specialCases.get(info.output) * 2 : specialCases.get(info.output);
            } else {
                // we didn't have the value in either lookup, fall back to 1/3 or 2/3
                // return which is only accurate for most values
                dmgBack = (int) (this.upgraded ? Math.ceil(info.output * 0.66) : Math.ceil(info.output * 0.33));
            }

            this.flash();
            int amt = dmgBack;
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, amt, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));

        }

        return damageAmount;
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
        return new AegisPower(this.upgraded);
    }
}
