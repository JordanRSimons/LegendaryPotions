package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;

import static legendarypotionsmod.BasicMod.makeID;

public class EchoPotion {
}

public class MyPotion extends BasePotion {
    public static final String ID = makeID(MyPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 0, 255);

}