package TheTemplar.glyphs;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphAboveCreatureAction;
import TheTemplar.powers.FlameOfHeavenPower;
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

    private static final int EVOKE_SINGLE = 4;
    private static final int EVOKE_DOUBLE = 10;

    public Justice() {
        super(classID, DESCRIPTION);
    }

    @Override
    public void evokeSingle() {
        doEffect(EVOKE_SINGLE);
    }

    @Override
    public void evokeDouble() {
        doEffect(EVOKE_DOUBLE);
    }

    private void doEffect(int amt) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    this.addToBot(new GlyphAboveCreatureAction(m, this));
                }
            }

            // increase damage by Vigor if you have Flame of Heaven and Vigor
            if (this.p.hasPower(FlameOfHeavenPower.POWER_ID) && this.p.hasPower(VigorPower.POWER_ID)) {
                amt += this.p.getPower(VigorPower.POWER_ID).amount;
                // if Flame of Heaven is upgraded, do not remove the Vigor
                if (!((FlameOfHeavenPower)this.p.getPower(FlameOfHeavenPower.POWER_ID)).upgraded) {
                    this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, VigorPower.POWER_ID));
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
