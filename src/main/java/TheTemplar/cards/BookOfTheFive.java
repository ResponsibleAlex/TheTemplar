package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.actions.EquipHolyWeaponAction;
import TheTemplar.characters.TheTemplar;
import TheTemplar.variables.HolyWeapons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class BookOfTheFive extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(BookOfTheFive.class.getSimpleName());
    public static final String IMG = makeCardPath(BookOfTheFive.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    // /STAT DECLARATION/


    public BookOfTheFive() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipHolyWeaponAction(HolyWeapons.Book, upgraded));
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
        return new BookOfTheFive();
    }
}
