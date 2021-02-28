package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.powers.*;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class HolyLight extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(HolyLight.class.getSimpleName());
    public static final String IMG = makeCardPath(HolyLight.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 0;
    private static final int BONUS = 2;
    private static final int UPGRADE_PLUS_BONUS = 1;

    // /STAT DECLARATION/


    public HolyLight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BONUS;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BONUS);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HolyLight();
    }
}
