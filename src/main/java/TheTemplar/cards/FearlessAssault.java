package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class FearlessAssault extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(FearlessAssault.class.getSimpleName());
    public static final String IMG = makeCardPath(FearlessAssault.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BONUS = 4;
    private static final int UPGRADE_PLUS_BONUS = 2;

    // /STAT DECLARATION/


    public FearlessAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS;

        this.glowEmpowered = true;
    }

    public void calculateCardDamage(AbstractMonster m) {
        int realBaseDamage = this.baseDamage;
        if (this.isEmpowered(m)) {
            this.baseDamage += this.magicNumber;
        }
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;

        if (isEmpowered(m)) {
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_BONUS);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FearlessAssault();
    }
}
