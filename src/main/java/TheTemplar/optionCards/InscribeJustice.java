package TheTemplar.optionCards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.cards.AbstractDynamicCard;
import TheTemplar.glyphs.Justice;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

public class InscribeJustice extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(InscribeJustice.class.getSimpleName());
    public static final String IMG = makeCardPath(InscribeJustice.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = -2;
    private final int number;

    // /STAT DECLARATION/


    public InscribeJustice() {
        this(1);
    }

    public InscribeJustice(int number) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.number = number;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        for (int i = 0; i < number; i++) {
            addToBot(new GlyphInscribeAction(new Justice()));
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
        return new InscribeJustice();
    }
}
