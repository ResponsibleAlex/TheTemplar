package TheTemplar.actions;

import TheTemplar.powers.RingingChallengePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class RingingChallengeAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final AbstractRoom room;

    private boolean isNormal = false;
    private boolean isElite = false;
    private boolean isBoss = false;

    private boolean isRandomUpgrade = false;
    private int maxHp = 0;

    public RingingChallengeAction() {
        player = AbstractDungeon.player;
        room = AbstractDungeon.getCurrRoom();

        if (room instanceof MonsterRoomBoss) {
            isBoss = true;
        } else if (room instanceof MonsterRoomElite) {
            isElite = true;
        } else if (room instanceof MonsterRoom) {
            isNormal = true;
        }

        actionType = ActionType.SPECIAL;
        duration = startDuration = Settings.ACTION_DUR_FAST;

        if (player.hasPower(RingingChallengePower.POWER_ID)) {
            isDone = true;
        }
    }

    public void update() {
        if (AbstractDungeon.getMonsters()
                           .areMonstersBasicallyDead()) {
            isDone = true;
        } else {
            if (duration == startDuration) {
                setEnemyBuffs();
                setRewards();

                addToBot(new ApplyPowerAction(player, player, new RingingChallengePower(maxHp, isRandomUpgrade)));
            }

            tickDuration();
        }
    }

    private void setEnemyBuffs() {
        MonsterGroup mg = AbstractDungeon.getMonsters();
        int amt;

        switch (AbstractDungeon.miscRng.random(0, 3)) {
            case 0:
                amt = AbstractDungeon.actNum + 1;
                mg.monsters.stream()
                           .map(m -> new ApplyPowerAction(m, m, new StrengthPower(m, amt), amt))
                           .forEach(this::addToBot);
                break;
            case 1:
                mg.monsters.stream()
                           .map(m -> new IncreaseMaxHpAction(m, 0.25F, true))
                           .forEach(this::addToBot);
                break;
            case 2:
                amt = AbstractDungeon.actNum * 2 + 2;
                mg.monsters.stream()
                           .map(m -> new ApplyPowerAction(m, m, new MetallicizePower(m, amt), amt))
                           .forEach(this::addToBot);
                break;
            case 3:
                amt = 1 + AbstractDungeon.actNum * 2;
                mg.monsters.stream()
                           .map(m -> new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, amt), amt))
                           .forEach(this::addToBot);
                break;
        }
    }

    private void setRewards() {
        int roll = AbstractDungeon.miscRng.random(0, 100);

        if (isNormal) {
            // 15 more gold +6% rare chance  OR  10 more gold +12% rare chance
            if (roll > 33) {
                room.addGoldToRewards(15);
                room.baseRareCardChance += 6;
                room.baseUncommonCardChance += 6;
            } else {
                room.addGoldToRewards(10);
                room.baseRareCardChance += 12;
                room.baseUncommonCardChance += 12;
            }
        } else if (isElite) {
            // 20 more gold   AND
            // random upgrade  AND/OR  Max HP
            room.addGoldToRewards(20);
            if (roll > 80) {
                isRandomUpgrade = true;
                maxHp = 2;
            } else if (roll > 33) {
                maxHp = 3;
            } else {
                isRandomUpgrade = true;
            }
        } else if (isBoss) {
            // +4 Max HP  AND  random upgrade
            isRandomUpgrade = true;
            maxHp = 4;
        }
    }
}
