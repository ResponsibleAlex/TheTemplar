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
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
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

        switch(AbstractDungeon.miscRng.random(0, 4)) {
            case 0:
                amt = AbstractDungeon.actNum + 1; // 2, 3, 4, 5
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
                amt = AbstractDungeon.actNum * 2 + 2; // 4, 6, 8, 10
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new MetallicizePower(m, amt), amt));
                    }
                }
                break;
            case 3:
                amt = 1 + AbstractDungeon.actNum * 2; // 3, 5, 7, 9
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, amt), amt));
                    }
                }
                break;
            case 4:
                amt = AbstractDungeon.actNum * 3 + 3; // 6, 9, 12, 15
                for (AbstractMonster m : mg.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, m, new PlatedArmorPower(m, amt), amt));
                    }
                }
                break;
        }
    }

    private void setRewards() {
        //int roll = AbstractDungeon.miscRng.random(0, 100);

        if (isNormal) {
            randomUpgrade = true;
        } else if (isElite) {
            r.addRelicToRewards(AbstractRelic.RelicTier.COMMON);
        } else if (isBoss) {
            maxHp = 5;
        }
    }
}
