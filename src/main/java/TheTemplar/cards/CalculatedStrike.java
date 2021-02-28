package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class CalculatedStrike extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(CalculatedStrike.class.getSimpleName());
    public static final String IMG = makeCardPath(CalculatedStrike.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/


    public CalculatedStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.onlyAttackInHand()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean onlyAttackInHand() {
        int expected;
        if (AbstractDungeon.player.hand.contains(this)) {
            // playing from hand, normal case
            expected = 1;
        } else {
            // playing from limbo, not in hand
            expected = 0;
        }

        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK) {
                count++;
            }
        }

        // we didn't find any other attacks, make sure the count matches expected
        return count == expected;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);

        if (this.onlyAttackInHand()) {
            this.damage *= 2;
        }
        this.isDamageModified = this.damage != this.baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CalculatedStrike();
    }
}
