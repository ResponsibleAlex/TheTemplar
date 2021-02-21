package TheTemplar.cards;

import TheTemplar.actions.VanguardGrowthAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Vanguard extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Vanguard.class.getSimpleName());
    public static final String IMG = makeCardPath(Vanguard.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    private static final int BASE_VALUE = 7;
    private static final int BONUS = 1;
    private static final int UPGRADE_PLUS_BONUS = 1;

    // /STAT DECLARATION/


    public Vanguard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = baseBlock = this.misc = BASE_VALUE;
        magicNumber = baseMagicNumber = BONUS;

        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VanguardGrowthAction(this.uuid, this.magicNumber));
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyPowers() {
        this.baseDamage = this.baseBlock = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BONUS);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Vanguard();
    }
}
