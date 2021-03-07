package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JusticeAction extends AbstractGameAction {

    private final int amt;
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
