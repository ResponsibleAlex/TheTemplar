package TheTemplar.cards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Charity;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.powers.RegenPower;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Tithe extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Tithe.class.getSimpleName());
    public static final String IMG = makeCardPath(Tithe.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 0;
    private static final int REGEN = 2;
    private static final int UPGRADE_PLUS_REGEN = 1;
    private static final int LOSE_GOLD = 10;

    // /STAT DECLARATION/


    public Tithe() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = REGEN;

        tags.add(CardTags.HEALING);
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("EVENT_PURCHASE");
        p.loseGold(LOSE_GOLD);

        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));

        addToBot(new GlyphInscribeAction(new Charity()));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_REGEN);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tithe();
    }
}
