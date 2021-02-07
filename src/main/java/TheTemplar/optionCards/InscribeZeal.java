package TheTemplar.optionCards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.cards.AbstractDynamicCard;
import TheTemplar.glyphs.Zeal;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

public class InscribeZeal extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(InscribeZeal.class.getSimpleName());
    public static final String IMG = makeCardPath(InscribeZeal.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private final int number;

    // /STAT DECLARATION/


    public InscribeZeal() {
        this(1);
    }

    public InscribeZeal(int number) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.number = number;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        for (int i = 0; i < this.number; i++) {
            this.addToBot(new GlyphInscribeAction(new Zeal()));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new InscribeZeal();
    }
}
