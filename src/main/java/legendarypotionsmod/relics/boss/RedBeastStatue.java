package legendarypotionsmod.relics.boss;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static legendarypotionsmod.legendarypotions.makeID;

public class RedBeastStatue extends BaseRelic {
    private static final String NAME = "RedBeastStatue"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public RedBeastStatue() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.potionSlots > 0) {
            p.potionSlots--;

            // If a potion exists in the rightmost slot, remove it
            if (p.potions.size() > p.potionSlots) {
                AbstractPotion removed = p.potions.get(p.potionSlots);
                if (removed != null && removed.isObtained) {
                    p.removePotion(removed);
                }
            }

            // Remove the visual slot manually
            if (p.potions.size() > p.potionSlots) {
                p.potions.remove(p.potions.size() - 1);
            }
        }
    }

    private boolean pendingPotion = false; // Flag to delay potion use

    @Override
    public void atBattleStart() {
        pendingPotion = true;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (pendingPotion) {
            pendingPotion = false;
            flash();

            // Queue an action to run potion usage on next frame, ensuring all draw/discard triggers run first
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    try {
                        Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
                        AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
                        potion.use(AbstractDungeon.player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
