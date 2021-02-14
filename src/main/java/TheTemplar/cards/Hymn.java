package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphInscribeMatchAction;
import TheTemplar.actions.GlyphInscribeRandomAction;
import TheTemplar.characters.TheTemplar;
import TheTemplar.glyphs.AbstractGlyph;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Hymn extends AbstractDynamicCard {

    private static final CardStrings cardStrings;

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Hymn.class.getSimpleName());
    public static final String IMG = makeCardPath(Hymn.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 1;


    // /STAT DECLARATION/


    public Hymn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractGlyph.canMatch()) {
            addToBot(new GlyphInscribeMatchAction());
        } else if (upgraded) {
            addToBot(new GlyphInscribeRandomAction());
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (!upgraded && !AbstractGlyph.canMatch()) {
                canUse = false;
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
            }

            return canUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hymn();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
