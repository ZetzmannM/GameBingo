package bingo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			String[][] ab = JSONInterface.fromJson("[{\"name\":\"Place a Cactus in a Flower Pot\",\"tooltip\":\"\"},{\"name\":\"6 Fire Charges\",\"tooltip\":\"\"},{\"name\":\"Emerald\",\"tooltip\":\"\"},{\"name\":\"3 Hay Bales\",\"tooltip\":\"\"},{\"name\":\"Potion of Slowness\",\"tooltip\":\"\"},{\"name\":\"Tame a Wolf\",\"tooltip\":\"\"},{\"name\":\"1 Magma Cream\",\"tooltip\":\"\"},{\"name\":\"Build a glass cube and fill the inner with lava\",\"tooltip\":\"\"},{\"name\":\"14 Note Blocks\",\"tooltip\":\"\"},{\"name\":\"10 Colours of Terracotta\",\"tooltip\":\"\"},{\"name\":\"5 Pumpkins\",\"tooltip\":\"\"},{\"name\":\"Place an Iron, Gold and Diamond block on top of each other\",\"tooltip\":\"\"},{\"name\":\"2 Bookshelves\",\"tooltip\":\"\"},{\"name\":\"Add a Marker to a Map\",\"tooltip\":\"\"},{\"name\":\"Tame a Horse\",\"tooltip\":\"\"},{\"name\":\"Put a Carpet on a Llama\",\"tooltip\":\"\"},{\"name\":\"3 Different Saplings\",\"tooltip\":\"\"},{\"name\":\"Level 25\",\"tooltip\":\"\"},{\"name\":\"Enchant an item\",\"tooltip\":\"\"},{\"name\":\"Heart of the Sea\",\"tooltip\":\"\"},{\"name\":\"Clean a Pattern off a Banner\",\"tooltip\":\"\"},{\"name\":\"Get a '... while trying to escape ...' Death message\",\"tooltip\":\"Example: 'PLAYER' drowned while trying to escape a Skeleton\"},{\"name\":\"Dig straight down to Bedrock from Sea level (1x1 hole)\",\"tooltip\":\"\"},{\"name\":\"Potion of Regeneration\",\"tooltip\":\"\"},{\"name\":\"2 Creepers in the same Boat\",\"tooltip\":\"The 2 Creepers must be in the same boat at the same time\"},{\"name\":\"Heart of the Sea\",\"tooltip\":\"\"},{\"name\":\"Water Bucket, Lava Bucket, Milk Bucket, Bucket of Fish\",\"tooltip\":\"Can be any of the types of Fish\"},{\"name\":\"Leash a Dolphin to a Fence\",\"tooltip\":\"\"},{\"name\":\"Destroy a Monster Spawner\",\"tooltip\":\"\"},{\"name\":\"Place a Cactus in a Flower Pot\",\"tooltip\":\"\"},{\"name\":\"62 Brick Blocks\",\"tooltip\":\"\"},{\"name\":\"Get a Skeleton's Bow\",\"tooltip\":\"Get the rare Bow item drop from a Skeleton\"},{\"name\":\"Damaged Anvil\",\"tooltip\":\"\"},{\"name\":\"Dead Bush\",\"tooltip\":\"\"},{\"name\":\"Build a Redstone AND Gate\",\"tooltip\":\"\"},{\"name\":\"7 Iron Blocks\",\"tooltip\":\"\"},{\"name\":\"3 Different Gold Items\",\"tooltip\":\"\"},{\"name\":\"Set fire to a Villager's House\",\"tooltip\":\"\"},{\"name\":\"2 Different Seeds\",\"tooltip\":\"Includes: Beetroot Seeds, Melon Seeds, Nether Wart, Pumpkin Seeds and (Wheat) Seeds. As per the 'A Seedy Place' advancement.\"},{\"name\":\"Golden Carrot\",\"tooltip\":\"\"},{\"name\":\"11 Obsidian\",\"tooltip\":\"\"},{\"name\":\"54 Rotten Flesh\",\"tooltip\":\"\"},{\"name\":\"Grow a Huge Mushroom\",\"tooltip\":\"\"},{\"name\":\"21 Item Frames\",\"tooltip\":\"\"},{\"name\":\"22 Magma Blocks\",\"tooltip\":\"\"},{\"name\":\"7 Colours of Glazed Terracotta\",\"tooltip\":\"\"},{\"name\":\"4 Mushroom Stew\",\"tooltip\":\"\"},{\"name\":\"Rabbit Stew\",\"tooltip\":\"\"},{\"name\":\"Activate a Nether Portal inside of a Village\",\"tooltip\":\"\"},{\"name\":\"Power a Redstone Lamp\",\"tooltip\":\"\"},{\"name\":\"FFF\",\"tooltip\":\"\"}]\r\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		new IntroGUI();
	}

}
