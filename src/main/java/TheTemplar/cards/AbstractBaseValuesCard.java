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

    @SuppressWarnings("unused")
    protected int increaseBaseDamage(AbstractMonster m) {
        return increaseBaseDamage();
    }
    protected int increaseBaseDamage() {
        return 0;
    }

    public void calculateCardDamage(AbstractMonster m) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.increaseBaseDamage(m);
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.increaseBaseDamage();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
