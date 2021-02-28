package TheTemplar.cards;

import TheTemplar.actions.ImbuedArmorAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class ImbuedArmor extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(ImbuedArmor.class.getSimpleName());
    public static final String IMG = makeCardPath(ImbuedArmor.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = -1;

    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int VIGOR = 3;
    private static final int UPGRADE_PLUS_VIGOR = 2;

    // /STAT DECLARATION/


    public ImbuedArmor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = VIGOR;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ImbuedArmorAction(p, magicNumber, block, freeToPlayOnce, energyOnUse));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_VIGOR);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ImbuedArmor();
    }
}
