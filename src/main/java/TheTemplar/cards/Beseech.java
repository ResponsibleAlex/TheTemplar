package TheTemplar.cards;

import TheTemplar.optionCards.BeseechChooseAttack;
import TheTemplar.optionCards.BeseechChooseSkill;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import java.util.ArrayList;

import static TheTemplar.TemplarMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings("unused")
public class Beseech extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Beseech.class.getSimpleName());
    public static final String IMG = makeCardPath(Beseech.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Beseech() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.blessing = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean setCost = this.triggerBlessing();

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new BeseechChooseAttack(this.upgraded, setCost));
        choices.add(new BeseechChooseSkill(this.upgraded, setCost));

        this.addToBot(new ChooseOneAction(choices));
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
        return new Beseech();
    }
}
