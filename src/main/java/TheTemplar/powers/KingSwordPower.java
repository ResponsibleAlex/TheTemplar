package TheTemplar.powers;

import TheTemplar.cards.KingSword;
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

public class KingSwordPower extends AbstractPower implements CloneablePowerInterface {

    private final String desc;
    private final float mult;
    private final boolean upgraded;
    private final int dmgAmt;

    public static final String POWER_ID = TemplarMod.makeID(KingSwordPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("KingSword84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("KingSword32.png"));

    public KingSwordPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;

        if (upgraded) {
            mult = 1.5f;
            desc = "50";
            this.dmgAmt = KingSword.DAMAGE_ALL + KingSword.UPGRADE_PLUS_DAMAGE_ALL;
        } else {
            mult = 1.25f;
            desc = "25";
            this.dmgAmt = KingSword.DAMAGE_ALL;
        }
        this.upgraded = upgraded;

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

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
            this.addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.dmgAmt, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.desc + DESCRIPTIONS[1] + this.dmgAmt + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new KingSwordPower(this.upgraded);
    }
}