package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Radiance extends AbstractBaseValuesCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Radiance.class.getSimpleName());
    public static final String IMG = makeCardPath(Radiance.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 2;

    private static final int DAMAGE = 10;
    private static final int BONUS = 3;
    private static final int UPGRADE_PLUS_BONUS = 2;

    // /STAT DECLARATION/


    public Radiance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS;
    }

    private int countBlessingCards() {
        return countBlessingPile(AbstractDungeon.player.hand)
                + countBlessingPile(AbstractDungeon.player.discardPile)
                + countBlessingPile(AbstractDungeon.player.drawPile);
    }

    private int countBlessingPile(CardGroup group) {
        int x = 0;
        for (AbstractCard c : group.group) {
            if (c instanceof AbstractDynamicCard) {
                if (((AbstractDynamicCard) c).blessing) {
                    x++;
                }
            }
        }
        return x;
    }

    @Override
    protected int increaseBaseDamage() {
        return magicNumber * countBlessingCards();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
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
        return new Radiance();
    }
}
