package legendarypotionsmod.relics.shop;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static legendarypotionsmod.legendarypotions.makeID;

public class NextShopsSH extends BaseRelic {
    private static final String NAME = "NextShopsSH"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public NextShopsSH() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        if (room instanceof ShopRoom) {
            flash();
            try {
                Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
                AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
                AbstractDungeon.player.obtainPotion(potion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean canSpawn() {
        return (com.megacrit.cardcrawl.core.Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }
}
