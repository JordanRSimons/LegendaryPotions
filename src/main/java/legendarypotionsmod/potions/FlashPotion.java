package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.legendarypotions.makeID;

public class FlashPotion extends BasePotion {
    public static final String ID = makeID(FlashPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(209, 206, 123); // sand yellow. light grey 164,171,170
    private static final Color HYBRID_COLOR = CardHelper.getColor(161, 27, 32); // blood red // darker blue
    private static final Color SPOTS_COLOR = null;

    public FlashPotion() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.S, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            int handSize = p.hand.size();

            // Discard entire hand
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, handSize, false));

            // Add 1 upgraded Flash of Steel per discarded card
            for (int i = 0; i < handSize; i++) {
                FlashOfSteel flashCard = new FlashOfSteel();
                flashCard.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(flashCard, 1));
            }

            // Add additional upgraded Flash of Steels based on potency (1 or 2 more)
            for (int i = 0; i < potency; i++) {
                FlashOfSteel flashCard = new FlashOfSteel();
                flashCard.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(flashCard, 1));
            }


        }
    }


}
