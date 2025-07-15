package legendarypotionsmod.potions;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import expansioncontent.cardmods.RetainCardMod;

import static legendarypotionsmod.legendarypotions.makeID;

public class BottledFinale extends BasePotion {
    public static final String ID = makeID(BottledFinale.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(161, 27, 32); // blood red
    private static final Color HYBRID_COLOR = CardHelper.getColor(46, 5, 6); // nearly black
    private static final Color SPOTS_COLOR = null;

    public BottledFinale() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.SPIKY, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;  // base potency 1, sacred bark bumps to 2
    }

    @Override
    public String getDescription() {
        // Use plural description when potency > 1
        if (potency == 1) {
            return String.format(DESCRIPTIONS[0], potency, potency, 10 * potency);
        } else {
            return String.format(DESCRIPTIONS[1], potency, potency, 10 * potency);
        }
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;

            // Add potency copies of Grand Finale to your hand (unupgraded)
            for (int i = 0; i < potency; i++) {
                AbstractCard finale = new GrandFinale();
                finale.upgrade();

                AbstractCard plans = new WellLaidPlans();
                plans.setCostForTurn(0);

                // Apply actual retain behavior via BaseMod
                //CardModifierManager.addModifier(finale, new RetainCardMod());

                addToBot(new MakeTempCardInHandAction(finale, 1));
                addToBot(new MakeTempCardInHandAction(plans, 1));
            }

            // Delay before scry+draw
            addToBot(new WaitAction(0.4F)); // 0.4 seconds delay (adjustable)

            // Scry then draw
            addToBot(new ScryAction(10 * potency));
            //addToBot(new DrawCardAction(p, 2 * potency));
        }
    }

    @Override
    public void addAdditionalTips() {

        // Tooltip for Scry
        this.tips.add(new PowerTip(
                TipHelper.capitalize(GameDictionary.SCRY.NAMES[0]),
                GameDictionary.keywords.get(GameDictionary.SCRY.NAMES[0])
        ));
    }

}