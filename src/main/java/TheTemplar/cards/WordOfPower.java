package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class WordOfPower extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(WordOfPower.class.getSimpleName());
    public static final String IMG = makeCardPath(WordOfPower.class.getSimpleName());
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/


    public WordOfPower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new DrawCardAction(AbstractDungeon.player, countTypesInscribedThisCombat()));
    }

    public void applyPowers() {
        super.applyPowers();
        setDescription(true);
    }

    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        setDescription(true);
    }

    @Override
    public void atTurnStart() {
        setDescription(true);
    }

    public void onMoveToDiscard() {
        setDescription(false);
    }

    private int countTypesInscribedThisCombat() {
        int typesInscribedThisCombat = 0;
        for (int i = 0; i < 5; i++) {
            if (TemplarMod.glyphTypesInscribedThisCombat[i]) {
                typesInscribedThisCombat++;
            }
        }
        return typesInscribedThisCombat;
    }

    private void setDescription(boolean includeTimes) {
        if (includeTimes) {
            magicNumber = countTypesInscribedThisCombat();
            isMagicNumberModified = true;
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            rawDescription = cardStrings.DESCRIPTION;
        }

        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            setDescription(false);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WordOfPower();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
