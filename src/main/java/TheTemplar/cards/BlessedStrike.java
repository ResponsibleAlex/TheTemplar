package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class BlessedStrike extends AbstractBaseValuesCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(BlessedStrike.class.getSimpleName());
    public static final String IMG = makeCardPath(BlessedStrike.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int BONUS = 3;
    private static final int UPGRADE_PLUS_BONUS = 3;

    // /STAT DECLARATION/


    public BlessedStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS;

        blessing = true;
        tags.add(CardTags.STRIKE);
    }

    @Override
    protected int increaseBaseDamage() {
        return (willTriggerBlessing() ? magicNumber : 0);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;

        if (triggerBlessing()) {
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));
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
        return new BlessedStrike();
    }
}
