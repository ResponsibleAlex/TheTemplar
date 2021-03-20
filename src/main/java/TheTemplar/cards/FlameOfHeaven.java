package TheTemplar.cards;

import TheTemplar.actions.EquipHolyWeaponAction;
import TheTemplar.variables.HolyWeapons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings("unused")
public class FlameOfHeaven extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(FlameOfHeaven.class.getSimpleName());
    public static final String IMG = makeCardPath(FlameOfHeaven.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 2;
    public static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    public static final int UPGRADED_BLOCK = BLOCK + UPGRADE_PLUS_BLOCK;

    // /STAT DECLARATION/


    public FlameOfHeaven() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BLOCK;
        cardsToPreview = new FireOfConviction();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipHolyWeaponAction(HolyWeapons.Torch, upgraded));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
            cardsToPreview.upgrade();
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlameOfHeaven();
    }
}
