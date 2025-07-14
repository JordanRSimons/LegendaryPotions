package legendarypotionsmod.relics.uncommon;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import legendarypotionsmod.relics.BaseRelic;

import static legendarypotionsmod.legendarypotions.makeID;

public class LeadBlock extends BaseRelic {
    private static final String NAME = "LeadBlock"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public LeadBlock() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUsePotion() {
        flash(); // Visual effect
        AbstractPlayer p = AbstractDungeon.player;

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new GainGoldAction(20));
        } else {
            p.gainGold(20);
        }
    }

}

