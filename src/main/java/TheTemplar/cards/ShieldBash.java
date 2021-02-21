package TheTemplar.cards;

import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.powers.BulwarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
public class ShieldBash extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(ShieldBash.class.getSimpleName());
    public static final String IMG = makeCardPath(ShieldBash.class.getSimpleName());
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;
    private static final int BULWARK = 3;
    private static final int UPGRADE_PLUS_BULWARK = 3;

    // /STAT DECLARATION/


    public ShieldBash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = 0;
        magicNumber = baseMagicNumber = BULWARK;
    }

    private void updateBaseDamage() {
        baseDamage = 0;
        if (AbstractDungeon.player.hasPower(BulwarkPower.POWER_ID)) {
            baseDamage = AbstractDungeon.player.getPower(BulwarkPower.POWER_ID).amount;
        }
        baseDamage += magicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBulwarkAction(magicNumber));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void applyPowers() {
        this.updateBaseDamage();
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.updateBaseDamage();
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
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
        return new ShieldBash();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
