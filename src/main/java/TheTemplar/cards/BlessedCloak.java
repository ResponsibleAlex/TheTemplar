package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

public class BlessedCloak extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(BlessedCloak.class.getSimpleName());
    public static final String IMG = makeCardPath(BlessedCloak.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int BLESSING_BLOCK = 3;

    // /STAT DECLARATION/


    public BlessedCloak() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = BLESSING_BLOCK;

        blessing = true;
    }



    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO - this should be done in applyPowers and ???
        int amt = block;
        if (triggerBlessing()) {
            amt = block + magicNumber;
        }

        addToBot(new GainBlockAction(p, p, amt));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlessedCloak();
    }
}
