package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.powers.FlameOfHeavenPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class JusticeAction extends AbstractGameAction {

    private int amt;
    private final boolean isMatchBonus;
    private final AbstractPlayer p;
    private final AbstractGlyph g;

    public JusticeAction(int amount, boolean isMatchBonus, AbstractGlyph glyph) {
        actionType = ActionType.DAMAGE;

        amt = amount;
        this.isMatchBonus = isMatchBonus;
        p = AbstractDungeon.player;
        g = glyph;
    }

    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

            if (!isMatchBonus) {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToTop(new GlyphAboveCreatureAction(m, g));
                    }
                }

                // increase damage by Vigor if you have Flame of Heaven and Vigor
                if (p.hasPower(FlameOfHeavenPower.POWER_ID) && p.hasPower(VigorPower.POWER_ID)) {
                    amt += p.getPower(VigorPower.POWER_ID).amount;
                    // if Flame of Heaven is upgraded, do not remove the Vigor
                    if (!((FlameOfHeavenPower) p.getPower(FlameOfHeavenPower.POWER_ID)).upgraded) {
                        addToTop(new RemoveSpecificPowerAction(p, p, VigorPower.POWER_ID));
                    }
                }
            }

            addToTop(
                    new DamageAllEnemiesAction(p,
                            DamageInfo.createDamageMatrix(amt, true),
                            DamageInfo.DamageType.THORNS,
                            AbstractGameAction.AttackEffect.FIRE,
                            true));
        }

        isDone = true;
    }
}
