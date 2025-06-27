package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.BasicMod.makeID;

public class EchoPotion extends BasePotion {
    public static final String ID = makeID(EchoPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    private static final Color SPOTS_COLOR = null;

    public EchoPotion() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.T, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        if (potency == 1) {
            return String.format(DESCRIPTIONS[0], potency) + String.format(DESCRIPTIONS[1], potency);
        } else {
            return String.format(DESCRIPTIONS[0], potency) + String.format(DESCRIPTIONS[2], potency);
        }
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < potency; i++) {
                AbstractCard echoForm = new EchoForm().makeCopy();
                echoForm.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(echoForm, 1, false));

                AbstractCard selfRepair = new SelfRepair();
                selfRepair.upgrade();  // Make it Self Repair+
                addToBot(new MakeTempCardInDiscardAction(selfRepair, 1));
            }
        }
    }
}