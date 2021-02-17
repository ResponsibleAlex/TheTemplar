package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.powers.FlameOfHeavenPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class JusticeAction extends AbstractGameAction {

    private int amt;
    private final boolean isMatchBonus;
    private final AbstractPlayer p;
    private final AbstractGlyph g;

    public JusticeAction(int amount, boolean isMatchBonus, AbstractGlyph glyph) {
        this.actionType = ActionType.DAMAGE;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;

        this.amt = amount;
        this.isMatchBonus = isMatchBonus;
        this.p = AbstractDungeon.player;
        this.g = glyph;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

                if (!isMatchBonus) {
                    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (!m.isDeadOrEscaped()) {
                            this.addToTop(new GlyphAboveCreatureAction(m, g));
                        }
                    }

                    // increase damage by Vigor if you have Flame of Heaven and Vigor
                    if (this.p.hasPower(FlameOfHeavenPower.POWER_ID) && this.p.hasPower(VigorPower.POWER_ID)) {
                        amt += this.p.getPower(VigorPower.POWER_ID).amount;
                        // if Flame of Heaven is upgraded, do not remove the Vigor
                        if (!((FlameOfHeavenPower) this.p.getPower(FlameOfHeavenPower.POWER_ID)).upgraded) {
                            this.addToTop(new RemoveSpecificPowerAction(this.p, this.p, VigorPower.POWER_ID));
                        }
                    }
                }

                this.addToTop(
                        new DamageAllEnemiesAction(this.p,
                                DamageInfo.createDamageMatrix(amt, true),
                                DamageInfo.DamageType.THORNS,
                                AbstractGameAction.AttackEffect.FIRE));
            }
        }

        this.isDone = true;
    }
}
