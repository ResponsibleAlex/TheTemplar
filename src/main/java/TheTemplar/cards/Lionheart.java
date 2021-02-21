package TheTemplar.cards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.actions.LionheartAttackAction;
import TheTemplar.glyphs.Valor;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

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
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

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
        int times = TemplarMod.valorInscribedThisCombat;
        if (this.upgraded) {
            this.addToBot(new GlyphInscribeAction(new Valor()));
            times++;
        }

        for (int i = 0; i < times; i++) {
            this.addToBot(new LionheartAttackAction(this));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.setDescription(true);
    }

    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        this.setDescription(true);
    }

    @Override
    public void atTurnStart() {
        this.setDescription(true);
    }

    public void onMoveToDiscard() {
        this.setDescription(false);
    }

    private void setDescription(boolean includeTimes) {
        if (includeTimes) {
            this.magicNumber = TemplarMod.valorInscribedThisCombat;
            this.isMagicNumberModified = true;
            if (this.upgraded) {
                // display an incremented amount to account for the Valor this card will Inscribe
                this.magicNumber++;
            }

            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }

        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + this.rawDescription;
        }

        this.initializeDescription();
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
