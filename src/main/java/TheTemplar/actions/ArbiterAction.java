package TheTemplar.actions;

import TheTemplar.glyphs.Justice;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.stream.IntStream;

public class ArbiterAction extends AbstractGameAction {
    private final boolean isFreePlayOnce;
    private final AbstractPlayer player;
    private final int energyOnUse;
    private final boolean isUpgraded;
    private final DamageAllEnemiesAction dmgAction;

    public ArbiterAction(AbstractPlayer player, boolean isUpgraded, boolean isFreePlayOnce, int energyOnUse, DamageAllEnemiesAction damageAllEnemiesAction) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.SPECIAL;
        this.player = player;
        this.isFreePlayOnce = isFreePlayOnce;
        this.energyOnUse = energyOnUse;
        this.isUpgraded = isUpgraded;
        dmgAction = damageAllEnemiesAction;
    }

    public void update() {
        int effect = getInitialEffect();

        if (player.hasRelic(ChemicalX.ID)) {
            effect += 2;
            player.getRelic(ChemicalX.ID)
                  .flash();
        }

        if (isUpgraded) {
            ++effect;
        }

        inscribeJusticeEffect(effect);

        // add the multi damage action to the bottom
        addToBot(dmgAction);

        if (!isFreePlayOnce) {
            player.energy.use(EnergyPanel.totalCount);
        }

        isDone = true;
    }

    private void inscribeJusticeEffect(int effect) {
        IntStream.range(0, effect)
                 .mapToObj(i -> new GlyphInscribeAction(new Justice()))
                 .forEach(this::addToTop);
    }

    private int getInitialEffect() {
        if (energyOnUse == -1) {
            return EnergyPanel.totalCount;
        }
        return energyOnUse;
    }
}
