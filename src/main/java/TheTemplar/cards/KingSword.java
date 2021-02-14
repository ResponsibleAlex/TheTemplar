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
public class KingSword extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(KingSword.class.getSimpleName());
    public static final String IMG = makeCardPath(KingSword.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 3;

    public static final int DAMAGE_ALL = 2;
    public static final int UPGRADE_PLUS_DAMAGE_ALL = 1;

    // /STAT DECLARATION/


    public KingSword() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DAMAGE_ALL;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipHolyWeaponAction(HolyWeapons.Sword, upgraded));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE_ALL);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new KingSword();
    }
}
