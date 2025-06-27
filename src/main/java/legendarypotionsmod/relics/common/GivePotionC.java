package legendarypotionsmod.relics.common;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static legendarypotionsmod.legendarypotions.makeID;

public class GivePotionC extends BaseRelic {
    private static final String NAME = "GivePotionC"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.COMMON; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public GivePotionC() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        try {
            Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
            AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
            AbstractDungeon.player.obtainPotion(potion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


