package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.powers.*;
import TheTemplar.util.HolyWeaponPower;
import TheTemplar.variables.HolyWeapons;
import TheTemplar.vfx.HolyWeaponEquipEffect;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EquipHolyWeaponAction extends AbstractGameAction {
    private final String weapon;
    private final boolean isUpgraded;
    private final boolean isSameWeapon;
    private final AbstractPlayer player;

    public EquipHolyWeaponAction(String holyWeapon, boolean isUpgraded) {
        weapon = holyWeapon;
        isSameWeapon = HolyWeapons.IsEquipped(holyWeapon);
        this.isUpgraded = isUpgraded;

        player = AbstractDungeon.player;
        actionType = ActionType.SPECIAL;
        duration = .5f;
        startDuration = .5f;
    }

    public void update() {
        if (duration == startDuration) {
            AbstractDungeon.effectsQueue.add(new HolyWeaponEquipEffect());
        }

        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0.0F) {
            TemplarMod.setHolyWeapon(weapon);
            setPower();

            isDone = true;
        }
    }

    private void setPower() {
        switch (weapon) {
            case HolyWeapons.Sword:
                setPower(new KingSwordPower(isUpgraded));
                break;
            case HolyWeapons.Hammer:
                setPower(new SacredHammerPower(isUpgraded));
                break;
            case HolyWeapons.Aegis:
                setPower(new AegisPower(isUpgraded));
                break;
            case HolyWeapons.Torch:
                setPower(new FlameOfHeavenPower(isUpgraded));
                break;
            case HolyWeapons.Book:
                setPower(new BookOfTheFivePower(isUpgraded));
                break;
            default:
                throw new RuntimeException("Unknown weapon: " + weapon);
        }
    }

    private void setPower(HolyWeaponPower power) {
        if (isSameWeapon) {
            HolyWeaponPower current = (HolyWeaponPower) player.getPower(power.ID);
            current.refresh(isUpgraded);
            current.flash();
        } else {
            removePowers();
            addToBot(new ApplyPowerAction(player, player, power, 0));
        }
    }

    private void removePowers() {
        addToBot(buildRemovePowerAction(SacredHammerPower.POWER_ID));
        addToBot(buildRemovePowerAction(FlameOfHeavenPower.POWER_ID));
        addToBot(buildRemovePowerAction(KingSwordPower.POWER_ID));
        addToBot(buildRemovePowerAction(AegisPower.POWER_ID));
        addToBot(buildRemovePowerAction(BookOfTheFivePower.POWER_ID));
    }

    private RemoveSpecificPowerAction buildRemovePowerAction(String powerId) {
        return new RemoveSpecificPowerAction(player, player, powerId);
    }
}
