package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;

import static legendarypotionsmod.BasicMod.makeID;

public class EchoPotion extends BasePotion {
    public static final String ID = makeID(EchoPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 0, 255);

    public EchoPotion() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

}