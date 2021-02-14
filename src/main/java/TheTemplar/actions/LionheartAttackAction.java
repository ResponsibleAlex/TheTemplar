package TheTemplar.actions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LionheartAttackAction extends AttackDamageRandomEnemyAction {

    public LionheartAttackAction(AbstractCard card) {
        super(card);
        attackEffect = AttackEffect.FIRE;
    }

    public void update() {
        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(0.1F));
        }

        super.update();
        if (target != null) {
            addToTop(new VFXAction(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect)));
        }
    }
}
