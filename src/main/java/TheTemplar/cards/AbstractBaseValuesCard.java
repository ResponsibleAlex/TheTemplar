package TheTemplar.cards;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractBaseValuesCard extends AbstractDynamicCard {
    public AbstractBaseValuesCard(final String id,
                                  final String img,
                                  final int cost,
                                  final CardType type,
                                  final CardColor color,
                                  final CardRarity rarity,
                                  final CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    protected int increaseBaseDamage() {
        return 0;
    }

    public void calculateCardDamage(AbstractMonster m) {
        int realBaseDamage = baseDamage;
        baseDamage += increaseBaseDamage();
        super.calculateCardDamage(m);
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = baseDamage;
        baseDamage += increaseBaseDamage();
        super.applyPowers();
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }
}
