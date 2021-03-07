package TheTemplar.powers;

import TheTemplar.cards.KingSword;
import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static TheTemplar.TemplarMod.makePowerPath;

public class KingSwordPower extends HolyWeaponPower implements CloneablePowerInterface {

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

    public void stackPower(int unused) { }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (AbstractCard.CardType.ATTACK == card.type) {
            flash();
            TemplarMod.flashCustomAttackAllEffect();
            addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(KingSword.DAMAGE_ALL, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            addToBot(new ApplyPowerAction(target, owner, new WeakPower(target, 1, false), 1));
            addToBot(new ApplyPowerAction(target, owner, new VulnerablePower(target, 1, false), 1));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (upgraded) {
            description += DESCRIPTIONS[1];
        }
        description += DESCRIPTIONS[2] + KingSword.DAMAGE_ALL + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new KingSwordPower(upgraded);
    }
}
