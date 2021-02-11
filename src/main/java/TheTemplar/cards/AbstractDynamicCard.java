package TheTemplar.cards;

import TheTemplar.TemplarMod;
import TheTemplar.powers.ParagonFormPower;
import TheTemplar.powers.ProtectiveBlessingPower;
import TheTemplar.relics.PrayerBeads;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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
    public void triggerOnGlowCheck() {
        if (this.willTriggerBlessing()) {
            this.glowColor = Color.MAGENTA.cpy();
        } else if (this.glowEmpowered && this.areAnyEmpowered()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean triggerBlessing() {
        if (this.willTriggerBlessing()) {

            AbstractPlayer p = AbstractDungeon.player;

            // trigger Protective Blessing
            if (p.hasPower(ProtectiveBlessingPower.POWER_ID)) {
                ((ProtectiveBlessingPower)(p.getPower(ProtectiveBlessingPower.POWER_ID))).trigger();
            }

            // trigger Paragon Form
            if (p.hasPower(ParagonFormPower.POWER_ID)) {
                ((ParagonFormPower)(p.getPower(ParagonFormPower.POWER_ID))).trigger();
            }

            // trigger Prayer Beads if this is the first blessing this turn
            if (!TemplarMod.triggeredBlessingThisTurn && p.hasRelic(PrayerBeads.ID)) {
                AbstractRelic r = p.getRelic(PrayerBeads.ID);
                r.flash();
                this.addToBot(new RelicAboveCreatureAction(p, r));
                this.addToBot(new GainEnergyAction(1));
            }

            TemplarMod.triggeredBlessingThisTurn = true;
            this.triggerNextBlessing = false;

            return true;
        }
        return false;
    }

    protected boolean willTriggerBlessing() {
        return (this.blessing &&
                (AbstractDungeon.player.hand.size() == 5
                        || this.triggerNextBlessing
                || AbstractDungeon.player.hasPower(ParagonFormPower.POWER_ID)));
    }

    public boolean isEmpowered(AbstractMonster m) {
        return TemplarMod.isEmpowered(m);
    }

    public boolean areAnyEmpowered() {
        return TemplarMod.areAnyEmpowered();
    }
}