package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Momentum extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Momentum.class.getSimpleName());
    public static final String IMG = makeCardPath(Momentum.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int EXTRA_TIMES = 1;
    private static final int UPGRADE_PLUS_TIMES = 1;

    // /STAT DECLARATION/


    public Momentum() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = EXTRA_TIMES;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int vigorBonus = 0;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
            vigorBonus = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount * this.magicNumber;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += vigorBonus;
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
    }

    @Override
    public void applyPowers() {
        int vigorBonus = 0;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
            vigorBonus = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount * this.magicNumber;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += vigorBonus;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_TIMES);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Momentum();
    }
}
