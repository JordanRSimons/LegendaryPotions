package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import static legendarypotionsmod.BasicMod.makeID;

public class GhostInABottle extends BasePotion {
    public static final String ID = makeID(GhostInABottle.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    private static final Color SPOTS_COLOR = null;

    public GhostInABottle() {
        super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.EYE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        String effectLine = String.format(DESCRIPTIONS[0], potency, potency);
        String revivalLine = (potency == 1) ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        return effectLine + revivalLine;
    }



    // Called when player tries to drink potion
    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;

        // Apply immediate buffs
        addToBot(new ApplyPowerAction(p, p, new IntangiblePower(p, potency), potency));
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, potency), potency));

        // Potion is consumed and removed automatically
    }

    // Let player choose to drink or save
    @Override
    public boolean canUse() {
        // Always true, let player drink it anytime
        return true;
    }

    // This method triggers when the player takes damage and the potion is still in the belt

    public int onLoseHp(int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.currentHealth - damageAmount <= 0) {
            flash();

            // Calculate heal amount based on potency
            float percent = (potency == 1) ? 0.4f : 0.8f;
            int healAmt = (int) (p.maxHealth * percent);
            if (healAmt < 1) healAmt = 1;

            // Heal the player and prevent death
            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, healAmt));

            // Remove this potion from belt (visually)
            int slot = p.potions.indexOf(this);
            if (slot >= 0) {
                AbstractDungeon.topPanel.destroyPotion(slot);
            }

            // Return 0 so player doesn’t actually lose HP
            return 0;
        }
        return damageAmount;
    }
}


