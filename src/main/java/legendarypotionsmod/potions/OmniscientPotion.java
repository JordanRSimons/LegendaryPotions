package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.cards.purple.Omniscience;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.BasicMod.makeID;

public class OmniscientPotion extends BasePotion {
    public static final String ID = makeID(OmniscientPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    private static final Color SPOTS_COLOR = null;

    public OmniscientPotion() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.BOTTLE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
       return String.format(DESCRIPTIONS[0], potency);
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < potency; i++) {
                AbstractCard omniscience = new Omniscience().makeCopy();
                omniscience.upgrade();
                addToBot(new MakeTempCardInHandAction(omniscience, 1, false));

                //AbstractCard omniscience = new Omniscience();
                //omniscience.upgrade();  // Make it Self Repair+
                addToBot(new MakeTempCardInDrawPileAction(omniscience, 2, true, true));
            }
        }
    }
}