package TheTemplar.actions;

import TheTemplar.glyphs.Justice;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ArbiterAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;
    private final boolean upgraded;
    private final DamageAllEnemiesAction dmgAction;

    public ArbiterAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, DamageAllEnemiesAction damageAllEnemiesAction) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.dmgAction = damageAllEnemiesAction;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (-1 != energyOnUse) {
            effect = energyOnUse;
        }

        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        if (upgraded) {
            ++effect;
        }

        // inscribe a Justice effect# times
        for (int i = 0; i < effect; i++) {
            this.addToTop(new GlyphInscribeAction(new Justice()));
        }
        // add the multi damage action to the bottom
        this.addToBot(dmgAction);

        if (!freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

        isDone = true;
    }
}
