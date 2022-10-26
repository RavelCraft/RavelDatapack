package com.connexal.raveldatapack;

import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.managers.EnchantmentManager;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventListener implements Listener {
    private final EnchantmentManager enchantmentManager = EasyDatapackAPI.getEnchantmentManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoin(PlayerJoinEvent event) {
        RavelDatapack.getPluginMessageManager().readQueuedPluginMessages();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVillagerAcquireTradeEvent(VillagerAcquireTradeEvent event) {
        if (this.enchantmentManager.getEnchantments().size() == 0) {
            return;
        }

        if (event.getEntity() instanceof WanderingTrader trader) {
            event.setCancelled(true);

            List<MerchantRecipe> recipes = new ArrayList<>();
            Random random = new Random();

            for (CustomEnchantment enchantment : this.enchantmentManager.getEnchantments()) {
                int level = random.nextInt(enchantment.getStartLevel(), enchantment.getMaxLevel() + 1);

                MerchantRecipe recipe = new MerchantRecipe(enchantment.getBook(level), 0, random.nextInt(5) + 10, true);
                recipe.addIngredient(new ItemStack(Material.BOOK));
                recipe.addIngredient(new ItemStack(Material.EMERALD, enchantment.getTradeCost(level)));
                recipes.add(recipe);
            }

            trader.setRecipes(recipes);

            trader.setCanDrinkMilk(true);
            trader.setCanDrinkPotion(true);
            trader.setDespawnDelay(0);
        }
    }
}
