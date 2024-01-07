package me.efekos.headhuntsmp.utils;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.config.GameConfig;
import me.efekos.headhuntsmp.exceptions.InvalidRecipeException;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnchorRecipeManager {
    private static ShapedRecipe lastLoadedRecipe;
    public static NamespacedKey namespace;


    public static ShapedRecipe getLastLoadedRecipe() {
        return lastLoadedRecipe;
    }

    public static ItemStack createAnchor(){
        ItemStack skull = new ItemStack(Material.RESPAWN_ANCHOR,1);
        ItemMeta meta = skull.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        assert meta != null;
        meta.setDisplayName(TranslateManager.translateColors(GameConfig.get().getString("unban-anchor.name")));
        meta.setLore(Collections.singletonList(TranslateManager.translateColors(GameConfig.get().getString("unban-anchor.description"))));

        assert container != null;
        container.set(new NamespacedKey(HeadHuntSMP.getPlugin(),"unban_anchor"), PersistentDataType.STRING,"yes");

        skull.setItemMeta(meta);

        return skull;
    }

    public static ShapedRecipe loadDefaultRecipe(JavaPlugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"revive_head"), createAnchor());

        recipe.shape("OOO","PPP","OOO");
        recipe.setIngredient('O',Material.OBSIDIAN);
        recipe.setIngredient('P',Material.PLAYER_HEAD);

        lastLoadedRecipe = recipe;
        return recipe;
    }

    public static ShapedRecipe loadConfigRecipe(JavaPlugin plugin) throws InvalidRecipeException {
        namespace = new NamespacedKey(plugin,"unban_anchor");

        ShapedRecipe recipe = new ShapedRecipe(namespace, createAnchor());

        List<String> shapeStrings = GameConfig.get().getStringList("unban-anchor.shape");

        recipe.shape(shapeStrings.get(0),shapeStrings.get(1),shapeStrings.get(2));

        ArrayList<Character> addedMaterials = new ArrayList<>();
        for (String shapeString:shapeStrings){

            // make sure shape piece is 3 chars
            if(shapeString.length()!=3) throw new InvalidRecipeException(shapeString + "Is "+shapeString.length()+" characters, but it must be 3.");

            for(char character:shapeString.toCharArray()){

                // get rid of registering same key more than one times
                if(!addedMaterials.contains(character)){
                    addedMaterials.add(character);

                    //make sure given key exists
                    String materialString = GameConfig.get().getString("unban-anchor.materials." + character);
                    if(materialString==null) throw new InvalidRecipeException("Key '"+character+"' is used in shape, but there is no material for this key.");

                    //make sure given key is a valid material
                    Material material = Material.matchMaterial(materialString);
                    if(material==null) throw new InvalidRecipeException("unban-anchor.materials."+character +" is not a valid material. Please see https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for valid materials.");

                    recipe.setIngredient(character, material);
                }

            }
        }

        lastLoadedRecipe = recipe;
        return recipe;
    }
}
