package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.characters.TheTemplar;
import TheTemplar.glyphs.Zeal;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Fanaticism extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Fanaticism.class.getSimpleName());
    public static final String IMG = makeCardPath(Fanaticism.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 0;
    private static final int ZEAL = 1;
    private static final int UPGRADE_PLUS_ZEAL = 1;

    // /STAT DECLARATION/


    public Fanaticism() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ZEAL;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new GlyphInscribeAction(new Zeal()));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ZEAL);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fanaticism();
    }
}
