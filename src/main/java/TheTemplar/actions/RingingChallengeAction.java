package TheTemplar.actions;

import TheTemplar.powers.RingingChallengePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class RingingChallengeAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractRoom r;

    private boolean isNormal = false;
    private boolean isElite = false;
    private boolean isBoss = false;

    private boolean randomUpgrade = false;
    private int maxHp = 0;

    public RingingChallengeAction() {
        this.p = AbstractDungeon.player;
        this.r = AbstractDungeon.getCurrRoom();

        if (this.r.getClass().isInstance(MonsterRoomBoss.class)) {
            this.isBoss = true;
        } else if (this.r.getClass().isInstance(MonsterRoomElite.class)) {
            this.isElite = true;
        } else if (this.r.getClass().isInstance(MonsterRoom.class)) {
            this.isNormal = true;
        }

        this.actionType = ActionType.SPECIAL;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;

        if (this.p.hasPower(RingingChallengePower.POWER_ID)) {
            this.isDone = true;
        }
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startDuration) {
                this.setEnemyBuffs();
                this.setRewards();

                this.addToBot(new ApplyPowerAction(p, p, new RingingChallengePower(this.maxHp, this.randomUpgrade)));
            }

            this.tickDuration();
        }
    }

    private void setEnemyBuffs() {
        MonsterGroup mg = AbstractDungeon.getMonsters();
        int amt;

        switch(AbstractDungeon.miscRng.random(0, 3)) {
            case 0:
                amt = AbstractDungeon.actNum + 1;
                for (AbstractMonster m : mg.monsters) {
                    this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, amt), amt));
                }
                break;
            case 1:
                for (AbstractMonster m : mg.monsters) {
                    this.addToBot(new IncreaseMaxHpAction(m, 0.25F, true));
                }
                break;
            case 2:
                amt = AbstractDungeon.actNum * 2 + 2;
                for (AbstractMonster m : mg.monsters) {
                    this.addToBot(new ApplyPowerAction(m, m, new MetallicizePower(m, amt), amt));
                }
                break;
            case 3:
                amt = 1 + AbstractDungeon.actNum * 2;
                for (AbstractMonster m : mg.monsters) {
                    this.addToBot(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, amt), amt));
                }
                break;
        }
    }

    private void setRewards() {
        int roll = AbstractDungeon.miscRng.random(0, 100);

        if (this.isNormal) {
            // 15 more gold +6% rare chance  OR  10 more gold +12% rare chance
            if (roll > 33) {
                this.r.addGoldToRewards(15);
                this.r.baseRareCardChance += 6;
                this.r.baseUncommonCardChance += 6;
            } else {
                this.r.addGoldToRewards(10);
                this.r.baseRareCardChance += 12;
                this.r.baseUncommonCardChance += 12;
            }
        } else if (this.isElite) {
            // 20 more gold   AND
            // random upgrade  AND/OR  Max HP
            this.r.addGoldToRewards(20);
            if (roll > 80) {
                this.randomUpgrade = true;
                this.maxHp = 2;
            } else if (roll > 33) {
                this.maxHp = 3;
            } else {
                this.randomUpgrade = true;
            }
        } else if (this.isBoss) {
            // +4 Max HP  AND  random upgrade
            this.randomUpgrade = true;
            this.maxHp = 4;
        }
    }
}
