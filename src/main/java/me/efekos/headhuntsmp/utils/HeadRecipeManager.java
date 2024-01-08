package me.efekos.headhuntsmp.utils;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.exceptions.InvalidRecipeException;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeadRecipeManager {
    private static ShapedRecipe lastLoadedRecipe;

    public static ShapedRecipe getLastLoadedRecipe() {
        return lastLoadedRecipe;
    }

    private static ItemStack createHead() {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        meta.setDisplayName(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("extra-head.name", "&eExtra Head")));
        meta.setLore(Collections.singletonList(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("extra-head.description", "&6An extra head to use!"))));

        container.set(new NamespacedKey(HeadHuntSMP.getPlugin(), "extra-head"), PersistentDataType.STRING, "yes");

        skull.setItemMeta(meta);

        return skull;
    }

    public static void loadDefaultRecipe(JavaPlugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "revive_head"), createHead());

        recipe.shape("DSD", "CNR", "DZD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('S', Material.SKELETON_SKULL);
        recipe.setIngredient('C', Material.CREEPER_HEAD);
        recipe.setIngredient('R', Material.DRAGON_HEAD);
        recipe.setIngredient('Z', Material.ZOMBIE_HEAD);

        lastLoadedRecipe = recipe;
    }

    public static void loadConfigRecipe(JavaPlugin plugin) throws InvalidRecipeException {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "extra_head"), createHead());

        List<String> shapeStrings = HeadHuntSMP.gameConfig.getStringList("extra-head.shape");

        recipe.shape(shapeStrings.get(0), shapeStrings.get(1), shapeStrings.get(2));

        ArrayList<Character> addedMaterials = new ArrayList<>();
        for (String shapeString : shapeStrings) {

            // make sure shape piece is 3 chars
            if (shapeString.length() != 3)
                throw new InvalidRecipeException(shapeString + "Is " + shapeString.length() + " characters, but it must be 3.");

            for (char character : shapeString.toCharArray()) {

                // get rid of registering same key more than one times
                if (!addedMaterials.contains(character)) {
                    addedMaterials.add(character);

                    //make sure given key exists
                    String materialString = HeadHuntSMP.gameConfig.get().getString("extra-head.materials." + character);
                    if (materialString == null)
                        throw new InvalidRecipeException("Key '" + character + "' is used in shape, but there is no material for this key.");

                    //make sure given key is a valid material
                    Material material = Material.matchMaterial(materialString);
                    if (material == null)
                        throw new InvalidRecipeException("extra-head.materials." + character + " is not a valid material. Please see https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for valid materials.");

                    recipe.setIngredient(character, material);
                }

            }
        }

        lastLoadedRecipe = recipe;
    }
}
