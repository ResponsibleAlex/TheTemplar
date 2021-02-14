package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.actions.LionheartAction;
import TheTemplar.characters.TheTemplar;
import TheTemplar.glyphs.Valor;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Lionheart extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Lionheart.class.getSimpleName());
    public static final String IMG = makeCardPath(Lionheart.class.getSimpleName());
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 3;

    private static final int DAMAGE = 9;

    // /STAT DECLARATION/


    public Lionheart() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        isMultiDamage = false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new GlyphInscribeAction(new Valor()));
        }

        addToBot(new LionheartAction(this));
    }

    public void applyPowers() {
        super.applyPowers();
        setDescription(true);
    }

    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        setDescription(true);
    }

    public void onMoveToDiscard() {
        setDescription(false);
    }

    private void setDescription(boolean includeTimes) {
        if (includeTimes) {
            magicNumber = TemplarMod.valorInscribedThisCombat;
            isMagicNumberModified = true;
            if (upgraded) {
                // display an incremented amount to account for the Valor this card will Inscribe
                magicNumber++;
            }

            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            rawDescription = cardStrings.DESCRIPTION;
        }

        if (upgraded) {
            rawDescription = cardStrings.UPGRADE_DESCRIPTION + rawDescription;
        }

        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            setDescription(false);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lionheart();
    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
