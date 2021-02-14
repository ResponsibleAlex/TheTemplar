package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ThrillOfBattleAction extends AbstractGameAction {
    private final DamageInfo info;
    private final AbstractPlayer p;

    public ThrillOfBattleAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        actionType = ActionType.DAMAGE;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (shouldCancelAction()) {
            isDone = true;
            return;
        }

        tickDuration();
        if (!isDone) {
            return;
        }

        AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
        target.damage(info);
        int amt = target.lastDamageTaken;
        if (amt > 0) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(AbstractDungeon.player, amt), amt));
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        } else {
            addToTop(new WaitAction(0.1F));
        }
    }
}
