package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import java.util.stream.IntStream;

public class ImbuedArmorAction extends AbstractGameAction {
    private final boolean isFreeToPlayOnce;
    private final AbstractPlayer player;
    private final int energyOnUse;
    private final int vigor;
    private final int block;

    public ImbuedArmorAction(AbstractPlayer player, int vigor, int block, boolean isFreeToPlayOnce, int energyOnUse) {
        this.player = player;
        this.isFreeToPlayOnce = isFreeToPlayOnce;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.vigor = vigor;
        this.block = block;
    }

    public void update() {
        int effect = getInitialEffect();

        if (player.hasRelic(ChemicalX.ID)) {
            effect += 2;
            player.getRelic(ChemicalX.ID)
                  .flash();
        }

        // vigor and block effect# times
        if (effect > 0) {
            addToBot(new VFXAction(player, new FlameBarrierEffect(player.hb.cX, player.hb.cY), 0.1F));
        }
        IntStream.range(0, effect)
                 .forEach(i -> executeActions());

        if (!isFreeToPlayOnce) {
            player.energy.use(EnergyPanel.totalCount);
        }

        isDone = true;
    }

    private void executeActions() {
        addToBot(new ApplyPowerAction(player, player, new VigorPower(player, vigor), vigor));
        addToBot(new GainBlockAction(player, player, block));
    }

    private int getInitialEffect() {
        if (energyOnUse == -1) {
            return EnergyPanel.totalCount;
        }
        return energyOnUse;
    }
}
