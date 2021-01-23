package TheTemplar.cards;

import TheTemplar.patches.AbstractMonsterPatch;
import TheTemplar.powers.ParagonFormPower;
import TheTemplar.powers.ProtectiveBlessingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    protected boolean blessing = false;
    public boolean triggerNextBlessing = false;
    protected boolean glowEmpowered = false;

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        if (type == CardType.ATTACK && target == CardTarget.ALL_ENEMY) {
            this.isMultiDamage = true;
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        this.triggerNextBlessing = false;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.willTriggerBlessing()) {
            this.glowColor = Color.CORAL.cpy();
        } if (this.glowEmpowered && this.areAnyEmpowered()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean triggerBlessing() {
        if (this.willTriggerBlessing()) {

            // trigger Protective Blessing
            if (AbstractDungeon.player.hasPower(ProtectiveBlessingPower.POWER_ID)) {
                ((ProtectiveBlessingPower)(AbstractDungeon.player.getPower(ProtectiveBlessingPower.POWER_ID))).trigger();
            }

            // trigger Paragon Form
            if (AbstractDungeon.player.hasPower(ParagonFormPower.POWER_ID)) {
                ((ParagonFormPower)(AbstractDungeon.player.getPower(ParagonFormPower.POWER_ID))).trigger();
            }

            return true;
        }
        return false;
    }

    private boolean willTriggerBlessing() {
        return (this.blessing &&
                (AbstractDungeon.player.hand.size() == 5
                        || this.triggerNextBlessing
                || AbstractDungeon.player.hasPower(ParagonFormPower.POWER_ID)));
    }

    public boolean isEmpowered(AbstractMonster m) {
        return (m.hasPower(StrengthPower.POWER_ID) && m.getPower(StrengthPower.POWER_ID).amount > 0)
                || m.hasPower(MetallicizePower.POWER_ID)
                || m.hasPower(RegenerateMonsterPower.POWER_ID)
                || AbstractMonsterPatch.maxHpBuff.get(m);
    }

    public boolean areAnyEmpowered() {
        for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
            if (isEmpowered(m)) {
                return true;
            }
        }
        return false;
    }
}