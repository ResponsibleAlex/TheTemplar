package TheTemplar.util;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class HolyWeaponPower extends AbstractPower {
    public boolean upgraded = false;

    public void refresh(boolean isUpgraded) {
        upgraded = isUpgraded || upgraded;
        updateDescription();
    }
}
