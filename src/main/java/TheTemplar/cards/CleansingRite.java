package TheTemplar.cards;

import TheTemplar.powers.CleansingRitePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class CleansingRite extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(CleansingRite.class.getSimpleName());
    public static final String IMG = makeCardPath(CleansingRite.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int CARDS = 1;
    private static final int INSCRIBE = 1;


    // /STAT DECLARATION/


    public CleansingRite() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = INSCRIBE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new CleansingRitePower(1), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CleansingRite();
    }
}
