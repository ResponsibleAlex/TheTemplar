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
    private final String newWeapon;
    private final boolean upgraded;
    private final boolean sameWeapon;
    private final AbstractPlayer p;

    public EquipHolyWeaponAction(String holyWeapon, boolean upgraded) {
        this.newWeapon = holyWeapon;
        this.sameWeapon = HolyWeapons.IsEquipped(holyWeapon);
        this.upgraded = upgraded;

        this.p = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
        this.duration = this.startDuration = .5f;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.effectsQueue.add(new HolyWeaponEquipEffect());
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            TemplarMod.setHolyWeapon(newWeapon);
            this.setPower();

            this.isDone = true;
        }
    }

    private void setPower() {
        switch (this.newWeapon) {
            case HolyWeapons.Sword:
                this.setPower(new KingSwordPower(this.upgraded));
                break;
            case HolyWeapons.Hammer:
                this.setPower(new SacredHammerPower(this.upgraded));
                break;
            case HolyWeapons.Aegis:
                this.setPower(new AegisPower(this.upgraded));
                break;
            case HolyWeapons.Torch:
                this.setPower(new FlameOfHeavenPower(this.upgraded));
                break;
            case HolyWeapons.Book:
                this.setPower(new BookOfTheFivePower());
                break;
        }
    }
    private void setPower(HolyWeaponPower power) {
        if (this.sameWeapon) {
            HolyWeaponPower current = (HolyWeaponPower) p.getPower(power.ID);
            current.refresh(this.upgraded);
            current.flash();
        } else {
            this.removePowers();
            this.addToBot(new ApplyPowerAction(this.p, this.p, power, 0));
        }
    }

    private void removePowers() {
        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, SacredHammerPower.POWER_ID));
        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, FlameOfHeavenPower.POWER_ID));
        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, KingSwordPower.POWER_ID));
        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, AegisPower.POWER_ID));
        this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, BookOfTheFivePower.POWER_ID));
    }
}
