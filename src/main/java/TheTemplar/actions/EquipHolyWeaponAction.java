package TheTemplar.actions;

import TheTemplar.TemplarMod;
import TheTemplar.powers.*;
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
    private final AbstractPlayer p;

    public EquipHolyWeaponAction(String holyWeapon, boolean upgraded) {
        this.newWeapon = holyWeapon;
        this.upgraded = upgraded;
        this.p = AbstractDungeon.player;

        if (HolyWeapons.IsEquipped(holyWeapon)) {
            this.isDone = true;
        } else {
            this.duration = this.startDuration = .5f;
        }
    }

    public void update() {
        if (!this.isDone) {
            if (this.duration == this.startDuration) {
                AbstractDungeon.effectsQueue.add(new HolyWeaponEquipEffect());
            }

            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                TemplarMod.setHolyWeapon(newWeapon);
                this.setPower();

                this.isDone = true;
            }
        }
    }

    private void setPower() {
        this.removePowers();

        switch (this.newWeapon) {
            case HolyWeapons.Sword:
                this.addToBot(new ApplyPowerAction(this.p, this.p, new KingSwordPower(this.upgraded), 0));
                break;
            case HolyWeapons.Hammer:
                this.addToBot(new ApplyPowerAction(this.p, this.p, new SacredHammerPower(this.upgraded), 0));
                break;
            case HolyWeapons.Aegis:
                this.addToBot(new ApplyPowerAction(this.p, this.p, new AegisPower(this.upgraded), 0));
                break;
            case HolyWeapons.Torch:
                this.addToBot(new ApplyPowerAction(this.p, this.p, new FlameOfHeavenPower(this.upgraded), 0));
                break;
            case HolyWeapons.Book:
                this.addToBot(new ApplyPowerAction(this.p, this.p, new BookOfTheFivePower(), 0));
                break;
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
