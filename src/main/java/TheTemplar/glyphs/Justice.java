package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.powers.FlameOfHeavenPower;
import TheTemplar.powers.ResoluteWillPower;
import TheTemplar.relics.RunedArmor;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Justice extends AbstractGlyph {
    public static final String classID = Justice.class.getSimpleName();
    public static final String DESCRIPTION =
            BaseMod.getKeywordDescription(TemplarMod.getModID().toLowerCase() + ":" + classID);

    private static final int TRIGGER = 3;
    private static final int MATCH_BONUS = 6;

    public Justice() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void trigger() {
        int amt = TRIGGER;
        if (p.hasPower(ResoluteWillPower.POWER_ID)) {
            amt += p.getPower(ResoluteWillPower.POWER_ID).amount;
        }
        doEffect(amt, false);
    }

    @Override
    public void triggerMatchBonus() {
        int amt = MATCH_BONUS;
        if (p.hasRelic(RunedArmor.ID)) {
            amt *= 2;
        }
        doEffect(amt, true);
    }

    private void doEffect(int amt, boolean isMatchBonus) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

            if (!isMatchBonus) {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped()) {
                        this.addToBot(new GlyphAboveCreatureAction(m, this));
                    }
                }

                // increase damage by Vigor if you have Flame of Heaven and Vigor
                if (this.p.hasPower(FlameOfHeavenPower.POWER_ID) && this.p.hasPower(VigorPower.POWER_ID)) {
                    amt += this.p.getPower(VigorPower.POWER_ID).amount;
                    // if Flame of Heaven is upgraded, do not remove the Vigor
                    if (!((FlameOfHeavenPower) this.p.getPower(FlameOfHeavenPower.POWER_ID)).upgraded) {
                        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, VigorPower.POWER_ID));
                    }
                }
            }

            this.addToBot(
                    new DamageAllEnemiesAction(this.p,
                            DamageInfo.createDamageMatrix(amt, true),
                            DamageInfo.DamageType.THORNS,
                            AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
