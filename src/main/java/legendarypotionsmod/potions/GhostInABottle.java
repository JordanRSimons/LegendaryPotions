package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
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

    private boolean awaitingHeal = false;

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        int potency = this.potency;

        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, potency), potency));
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, potency), potency));
        awaitingHeal = true;
    }

    public void onPlayerDealtDamage(int damageAmount) {
        if (awaitingHeal && damageAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, damageAmount));
            awaitingHeal = false;
        }
    }

    /*@Override
    public boolean onPlayerDeath() {
        AbstractPlayer p = AbstractDungeon.player;

        float percent = (this.potency == 1) ? 0.4f : 0.8f;
        int healAmt = (int)(p.maxHealth * percent);
        if (healAmt < 1) healAmt = 1;

        p.heal(healAmt, true);

        // Remove the potion from the player's belt
        AbstractDungeon.topPanel.destroyPotion(this.slot);

        // Prevent death
        return false;
    } */
}


