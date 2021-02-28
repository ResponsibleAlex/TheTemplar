package TheTemplar.optionCards;

import TheTemplar.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class BeseechChooseSkill extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(BeseechChooseSkill.class.getSimpleName());
    public static final String IMG = makeCardPath(BeseechChooseSkill.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = -2;

    // /STAT DECLARATION/

    private final boolean upgradeCard;
    private final boolean setCost;

    public BeseechChooseSkill(boolean upgradeCard, boolean setCost) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.upgradeCard = upgradeCard;
        this.setCost = setCost;

        if (upgradeCard) {
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        if (setCost) {
            rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();

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
            initializeDescription();
        }
    }

    private static final CardStrings cardStrings;
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeseechChooseSkill(upgradeCard, setCost);
    }
}
