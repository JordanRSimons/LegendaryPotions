package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static legendarypotionsmod.BasicMod.makeID;

public class EchoPotion extends BasePotion {
    public static final String ID = makeID(EchoPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = null;

    public EchoPotion() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        if (potency == 1) {
            return String.format(DESCRIPTIONS[0], potency) + DESCRIPTIONS[1];
        } else {
            return String.format(DESCRIPTIONS[0], potency) + DESCRIPTIONS[2];
        }
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < potency; i++) {
                AbstractCard echoForm = new EchoForm().makeCopy();
                echoForm.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(echoForm, 1, false));
            }
        }
    }
}