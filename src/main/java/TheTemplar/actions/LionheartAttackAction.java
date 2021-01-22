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
        this.attackEffect = AttackEffect.FIRE;
    }

    public void update() {
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(0.1F));
        }

        super.update();
        if (this.target != null) {
            this.addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
        }
    }
}
