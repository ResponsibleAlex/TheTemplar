package TheTemplar.powers;

import TheTemplar.cards.KingSword;
import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;

import static TheTemplar.TemplarMod.makePowerPath;

public class KingSwordPower extends HolyWeaponPower implements CloneablePowerInterface {

    private String desc;
    private float mult;
    private int dmgAmt;

    public static final String POWER_ID = TemplarMod.makeID(KingSwordPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("KingSword84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("KingSword32.png"));

    public KingSwordPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
    }

    @Override
    public void refresh(boolean upgraded) {
        this.upgraded = upgraded || this.upgraded;

        if (this.upgraded) {
            mult = 1.5f;
            desc = "50";
            dmgAmt = KingSword.DAMAGE_ALL + KingSword.UPGRADE_PLUS_DAMAGE_ALL;
        } else {
            mult = 1.25f;
            desc = "25";
            dmgAmt = KingSword.DAMAGE_ALL;
        }

        updateDescription();
    }

    public void stackPower(int unused) { }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK && type == DamageInfo.DamageType.NORMAL) {
            return damage * mult;
        } else {
            return damage;
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (AbstractCard.CardType.ATTACK == card.type) {
            flash();
            TemplarMod.flashCustomAttackAllEffect();
            addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(dmgAmt, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + desc + DESCRIPTIONS[1] + dmgAmt + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new KingSwordPower(upgraded);
    }
}
