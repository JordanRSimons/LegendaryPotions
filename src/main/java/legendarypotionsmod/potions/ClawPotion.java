package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    private static final Color LIQUID_COLOR = CardHelper.getColor(30, 138, 18); // light dark green
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 87, 17); // dark green
    private static final Color SPOTS_COLOR = null;

    public ClawPotion() {
        super(ID, 2, PotionRarity.PLACEHOLDER, PotionSize.H, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        isThrown = true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0]  + potency + DESCRIPTIONS[1];
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;

            // Open selection screen — allow selection of any number of cards
            AbstractDungeon.gridSelectScreen.open(
                    new ArrayList<>(p.hand.group),
                    99, // max selectable — 99 is functionally "any number"
                    "Select cards to exhaust.",
                    false,
                    true,  // can pick 0 cards
                    false,
                    false
            );

            // Queue a follow-up action to exhaust and reward
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        int count = AbstractDungeon.gridSelectScreen.selectedCards.size();

                        for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                            p.hand.moveToExhaustPile(card);
                        }

                        for (int i = 0; i < count * potency; i++) {
                            addToTop(new MakeTempCardInHandAction(new Claw(), 1, false));
                        }

                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    }

                    isDone = true;
                }
            });
        }
    }
}

