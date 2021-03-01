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
        p = AbstractDungeon.player;
        r = AbstractDungeon.getCurrRoom();

        if (r instanceof MonsterRoomBoss) {
            isBoss = true;
        } else if (r instanceof MonsterRoomElite) {
            isElite = true;
        } else if (r instanceof MonsterRoom) {
            isNormal = true;
        }

        actionType = ActionType.SPECIAL;
        duration = startDuration = Settings.ACTION_DUR_FAST;

        if (p.hasPower(RingingChallengePower.POWER_ID)) {
            isDone = true;
        }
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
        } else {
            if (duration == startDuration) {
                setEnemyBuffs();
                setRewards();

                addToBot(new ApplyPowerAction(p, p, new RingingChallengePower(maxHp, randomUpgrade)));
            }

            tickDuration();
        }
    }

    private void setEnemyBuffs() {
        MonsterGroup mg = AbstractDungeon.getMonsters();
        int amt;

        switch(AbstractDungeon.miscRng.random(0, 3)) {
            case 0:
                amt = AbstractDungeon.actNum + 1;
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, amt), amt));
                    }
                }
                break;
            case 1:
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new IncreaseMaxHpAction(m, 0.25F, true));
                    }
                }
                break;
            case 2:
                amt = AbstractDungeon.actNum * 2 + 2;
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new MetallicizePower(m, amt), amt));
                    }
                }
                break;
            case 3:
                amt = 1 + AbstractDungeon.actNum * 2;
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, amt), amt));
                    }
                }
                break;
        }
    }

    private void setRewards() {
        int roll = AbstractDungeon.miscRng.random(0, 100);

        if (isNormal) {
            // 20 more gold +6% rare chance  OR  10 more gold +12% rare chance
            if (roll > 33) {
                r.addGoldToRewards(20);
                r.baseRareCardChance += 6;
                r.baseUncommonCardChance += 6;
            } else {
                r.addGoldToRewards(10);
                r.baseRareCardChance += 12;
                r.baseUncommonCardChance += 12;
            }
        } else if (isElite) {
            // 25 more gold   AND
            // random upgrade  AND/OR  Max HP
            r.addGoldToRewards(25);
            if (roll > 80) {
                randomUpgrade = true;
                maxHp = 2;
            } else if (roll > 33) {
                maxHp = 3;
            } else {
                randomUpgrade = true;
            }
        } else if (isBoss) {
            // +4 Max HP  AND  random upgrade
            randomUpgrade = true;
            maxHp = 4;
        }
    }
}
