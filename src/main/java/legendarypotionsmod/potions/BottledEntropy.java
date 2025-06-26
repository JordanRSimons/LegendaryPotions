package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.potions.EntropicBrew;

import static legendarypotionsmod.BasicMod.makeID;

public class BottledEntropy extends BasePotion {
    public static final String ID = makeID(BottledEntropy.class.getSimpleName());

    //private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    //private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    //private static final Color SPOTS_COLOR = null;


    //public BottledEntropy() {
    //super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.S, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    //isThrown = true;
    //}

    public BottledEntropy() {
        super(ID, "Bottled Entropy", PotionRarity.PLACEHOLDER, PotionSize.M, PotionEffect.RAINBOW, Color.WHITE, null, null);
        isThrown = false;
        targetRequired = false;
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1; // or 2, or logic if you want to scale it
    }

    @Override
    public String getDescription() {
        if (potency == 1) {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[2];
        }
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;

        // Step 1: Gain potion slots (capped at 5)
        int maxAllowed = 5;
        int newMax = Math.min(p.potionSlots + potency, maxAllowed);
        int slotsToAdd = newMax - p.potionSlots;

        if (slotsToAdd > 0) {
            p.potionSlots += slotsToAdd;

            // Add nulls to the potions list for new empty slots
            for (int i = 0; i < slotsToAdd; i++) {
                p.potions.add(null);
            }
        }

        // Step 2: Fill all empty slots with Entropic Brew
        for (int i = 0; i < p.potionSlots; i++) {
            if (p.potions.get(i) == null) {
                p.obtainPotion(i, new EntropicBrew());
            }
        }
    }
}

