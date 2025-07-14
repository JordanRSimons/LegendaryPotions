package legendarypotionsmod.relics.rare;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.potions.OldLegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.relicRng;
import static legendarypotionsmod.legendarypotions.makeID;

public class DrinkTicket extends BaseRelic {
    private static final String NAME = "DrinkTicket"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public DrinkTicket() {
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

            // Subtract 50 gold, but don't let gold go negative
            // AbstractDungeon.player.gold = Math.max(0, AbstractDungeon.player.gold - 50);

            LegendaryPotionPool.loadPool();
            AbstractPotion potion = LegendaryPotionPool.getRandomLegendaryPotion(relicRng, false).makeCopy();
            AbstractDungeon.player.obtainPotion(potion);
        }
    }

    @Override
    public boolean canSpawn() {
        return (com.megacrit.cardcrawl.core.Settings.isEndless || AbstractDungeon.floorNum <= 34);
    }
}
