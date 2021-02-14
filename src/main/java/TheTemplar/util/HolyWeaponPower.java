package TheTemplar.util;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class HolyWeaponPower extends AbstractPower {
    public boolean upgraded = false;

    public void refresh(boolean upgraded) {
        this.upgraded = upgraded || this.upgraded;
        updateDescription();
    }
}
