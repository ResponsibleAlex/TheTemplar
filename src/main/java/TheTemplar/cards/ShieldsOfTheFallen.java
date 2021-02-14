package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class ShieldsOfTheFallen extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(ShieldsOfTheFallen.class.getSimpleName());
    public static final String IMG = makeCardPath(ShieldsOfTheFallen.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BULWARK = 5;
    private static final int UPGRADE_PLUS_BULWARK = 3;

    // /STAT DECLARATION/


    public ShieldsOfTheFallen() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BULWARK;

        isEthereal = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBulwarkAction(magicNumber));
        addToBot(new DrawCardAction(p, 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BULWARK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShieldsOfTheFallen();
    }
}
