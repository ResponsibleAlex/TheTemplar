package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class VanguardGrowthAction extends AbstractGameAction {
    private final int miscIncrease;
    private final UUID uuid;

    public VanguardGrowthAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        uuid = targetUUID;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (c.uuid.equals(uuid)) {
                c.misc += miscIncrease;
                c.applyPowers();
                c.baseBlock = c.misc;
                c.baseDamage = c.misc;
                c.isBlockModified = false;
                c.isDamageModified = false;
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(uuid)) {
            c.baseBlock = c.misc;
            c.baseDamage = c.misc;
            c.misc += miscIncrease;
            c.applyPowers();
        }

        isDone = true;
    }
}
