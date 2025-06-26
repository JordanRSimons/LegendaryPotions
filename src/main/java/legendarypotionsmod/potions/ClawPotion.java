package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static legendarypotionsmod.BasicMod.makeID;

public class ClawPotion extends BasePotion {
    public static final String ID = makeID(ClawPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    private static final Color SPOTS_COLOR = null;

    public ClawPotion() {
        super(ID, 2, PotionRarity.PLACEHOLDER, PotionSize.S, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        isThrown = true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0]  + potency + DESCRIPTIONS[1];
    }


    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            int count = p.hand.size();

            // Copy hand to avoid modification while iterating
            ArrayList<AbstractCard> handCopy = new ArrayList<>(p.hand.group);

            for (AbstractCard card : handCopy) {
                addToBot(new ExhaustSpecificCardAction(card, p.hand));
            }

            // Add 2 × potency Claws per exhausted card
            for (int i = 0; i < count * potency; i++) {
                addToBot(new MakeTempCardInHandAction(new Claw(), 1, false));
            }
        }
    }
}

