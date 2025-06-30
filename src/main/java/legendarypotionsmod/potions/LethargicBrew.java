package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Adrenaline;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.legendarypotions.makeID;

public class LethargicBrew extends BasePotion {
    public static final String ID = makeID(LethargicBrew.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(36, 155, 22); // light dark green 30,138,18
    private static final Color HYBRID_COLOR = CardHelper.getColor(16, 66, 10); // dark green 24,87,17
    private static final Color SPOTS_COLOR = null;

    public LethargicBrew() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        isThrown = true;
    }

    public String getDescription() {
        if (potency == 1) {
            return String.format(DESCRIPTIONS[0], potency) + String.format(DESCRIPTIONS[1], potency*4);
        } else {
            return String.format(DESCRIPTIONS[0], potency) + String.format(DESCRIPTIONS[2], potency*4);
        }
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
                AbstractCard adrenaline = new Adrenaline().makeCopy();
                adrenaline.upgrade();
                addToBot(new MakeTempCardInHandAction(adrenaline, 1, false));
                addToBot(new MakeTempCardInDrawPileAction(adrenaline, 4, true, true));
            }
        }
    }

    @Override
    public void addAdditionalTips() {

        // Tooltip for Slow
        this.tips.add(new PowerTip(
                "Slow",
                "Whenever you play a card, the enemy receives #b10% more damage from #yAttacks this turn."
        ));

    }


}
