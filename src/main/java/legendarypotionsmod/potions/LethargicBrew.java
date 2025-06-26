package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.BasicMod.makeID;

public class LethargicBrew extends BasePotion {
    public static final String ID = makeID(LethargicBrew.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(30, 138, 18); // light dark green
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 87, 17); // dark green
    private static final Color SPOTS_COLOR = null;

    public LethargicBrew() {
        super(ID, 2, PotionRarity.PLACEHOLDER, PotionSize.H, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        isThrown = true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0]  + potency + DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.hasPower(SlowPower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new SlowPower(monster, 0), 0));
                }
            }

            // Add 2 * potency Miracle cards to hand
            for (int i = 0; i < potency; i++) {
                AbstractCard miracle = new Miracle().makeCopy();
                addToBot(new MakeTempCardInHandAction(miracle, 1, false));
            }
        }
    }

}
