package TheTemplar.cards;

import TheTemplar.optionCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import java.util.ArrayList;

import static TheTemplar.TemplarMod.makeCardPath;

public class TestGlyphs extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(TestGlyphs.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 0;
    // private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 0;
    private static final int UPGRADE_PLUS_BLOCK = 0;

    // /STAT DECLARATION/


    public TestGlyphs() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        this.exhaust = true;
        this.isInnate = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> glyphChoices = new ArrayList<>();
        glyphChoices.add(new InscribeJustice());
        glyphChoices.add(new InscribeFortitude());
        glyphChoices.add(new InscribeValor());
        glyphChoices.add(new InscribeZeal());
        glyphChoices.add(new InscribeCharity());

        this.addToBot(new ChooseOneAction(glyphChoices));

        this.addToBot(new MakeTempCardInHandAction(new TestGlyphs()));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TestGlyphs();
    }
}
