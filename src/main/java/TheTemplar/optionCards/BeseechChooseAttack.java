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

    private final boolean upgrade;
    private final boolean setCost;

    public BeseechChooseAttack(boolean upgrade, boolean setCost) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.upgrade = upgrade;
        this.setCost = setCost;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();

        if (this.upgrade) {
            c.upgrade();
        }
        if (this.setCost) {
            c.setCostForTurn(0);
        }

        this.addToBot(new MakeTempCardInHandAction(c, true));
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
        return new BeseechChooseAttack(this.upgrade, this.setCost);
    }
}
