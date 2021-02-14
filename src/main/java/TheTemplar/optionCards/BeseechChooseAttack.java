package TheTemplar.optionCards;

import TheTemplar.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings("unused")
public class BeseechChooseAttack extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(BeseechChooseAttack.class.getSimpleName());
    public static final String IMG = makeCardPath(BeseechChooseAttack.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = -2;

    // /STAT DECLARATION/

    private final boolean upgradeCard;
    private final boolean setCost;

    public BeseechChooseAttack(boolean upgradeCard, boolean setCost) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.upgradeCard = upgradeCard;
        this.setCost = setCost;
        if (setCost) {
            upgrade();
        }
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();

        if (upgradeCard) {
            c.upgrade();
        }
        if (setCost) {
            c.setCostForTurn(0);
        }

        addToBot(new MakeTempCardInHandAction(c, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeseechChooseAttack(upgradeCard, setCost);
    }
}
