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
        AbstractDungeon.player.masterDeck.group.stream()
                                               .filter(c -> c.uuid.equals(uuid))
                                               .forEach(this::applyMasterDeckPowers);

        GetAllInBattleInstances.get(uuid)
                               .forEach(this::applyBattleCards);

        isDone = true;
    }

    private void applyMasterDeckPowers(AbstractCard c) {
        c.misc += miscIncrease;
        c.applyPowers();
        c.baseBlock = c.misc;
        c.baseDamage = c.misc;
        c.isBlockModified = false;
    }

    private void applyBattleCards(AbstractCard c) {
        c.baseBlock = c.misc;
        c.baseDamage = c.misc;
        c.misc += miscIncrease;
        c.applyPowers();
    }
}
