package TheTemplar.actions;

import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.powers.FlameOfHeavenPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class JusticeAction extends AbstractGameAction {

    private final boolean isMatchBonus;
    private final AbstractPlayer player;
    private final AbstractGlyph glyph;
    private int amount;

    public JusticeAction(int amount, boolean isMatchBonus, AbstractGlyph glyph) {
        actionType = ActionType.DAMAGE;
        duration = startDuration = Settings.ACTION_DUR_XFAST;

        this.amount = amount;
        this.isMatchBonus = isMatchBonus;
        player = AbstractDungeon.player;
        this.glyph = glyph;
    }

    public void update() {
        if (duration == startDuration && !AbstractDungeon.getMonsters()
                                                         .areMonstersBasicallyDead()) {
            if (!isMatchBonus) {
                AbstractDungeon.getMonsters().monsters.stream()
                                                      .filter(m -> !m.isDeadOrEscaped())
                                                      .map(m -> new GlyphAboveCreatureAction(m, glyph))
                                                      .forEach(this::addToTop);

                // increase damage by Vigor if you have Flame of Heaven and Vigor
                if (player.hasPower(FlameOfHeavenPower.POWER_ID) && player.hasPower(VigorPower.POWER_ID)) {
                    amount += player.getPower(VigorPower.POWER_ID).amount;
                    // if Flame of Heaven is upgraded, do not remove the Vigor
                    if (!((FlameOfHeavenPower) player.getPower(FlameOfHeavenPower.POWER_ID)).upgraded) {
                        addToTop(new RemoveSpecificPowerAction(player, player, VigorPower.POWER_ID));
                    }
                }
            }

            addToTop(
                    new DamageAllEnemiesAction(player,
                            DamageInfo.createDamageMatrix(amount, true),
                            DamageInfo.DamageType.THORNS,
                            AbstractGameAction.AttackEffect.FIRE));
        }

        isDone = true;
    }
}
