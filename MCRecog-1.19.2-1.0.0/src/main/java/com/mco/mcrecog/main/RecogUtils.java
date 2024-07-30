package com.mco.mcrecog.main;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecogUtils {

	/** Instance of Random */
	public static final Random rand = new Random();
	/** Array of random useless items to select from */
	public static final List<Item> USELESS_ITEMS = Arrays.asList(
			Items.WHEAT_SEEDS, Items.BONE_MEAL, Items.TROPICAL_FISH, Items.OAK_LEAVES, Items.WET_SPONGE, Items.SEAGRASS,
			Items.DEAD_BUSH, Items.SNOW, Items.BROWN_CARPET, Items.HORN_CORAL_BLOCK, Items.DEAD_BRAIN_CORAL,
			Items.CRIMSON_BUTTON, Items.CLAY_BALL, Items.COCOA_BEANS, Items.PUFFERFISH, Items.ROTTEN_FLESH, Items.SMALL_AMETHYST_BUD
	);

	public static final int SPLAT_START = 300;
	public static final int TONY_TICKS = 90;

	public static List<String> TRIGGERS = Arrays.asList(
			"crow", "pig", "sub", "follow", "cave", "yike", "day", "troll", "high", "diamond", "craft", "rot", "bone", "dream",
			"end", "dragon", "boat", "no shot", "bear", "axolotl", "creep", "rod", "nether", "bed", "twitch",
			"coal", "iron", "gold", "mod", "port", "water", "block", "village", "mine", "gam", "light", "ink", "bud",
			"poggers", "blessmepapi", "thing", "godlike", "tony"
	);

	public static List<String> RESPONSES = Arrays.asList(
			"Explode and die", "Lose all hunger", "Lose something random", "Big hole", "Mining fatigue", "Lava source block", "Set time to night",
			"Drop inventory", "Launched in the air", "Set to half a heart", "Surround in obsidian", "Spawn 10 zombies",
			"Spawn 10 skeletons", "Instant death", "Spawn 10 angry endermen", "Play dragon noise, spawn 10 endermite", "Fill inventory with boats",
			"Lose 10 arrows", "Spawn 7 hostile polar bears", "Axolotl time", "Spawn 7 creepers",
			"Spawn 7 blazes", "Spawn 7 wither skeletons", "Spawn 7 phantoms", "Spawn supercharged creeper", "Set on fire",
			"Spawn aggro iron golem", "Spawn pigmen", "Shuffle inventory", "Teleport randomly", "In water", "Spawn killer rabbits",
			"Spawn witches", "Give something useless", "Random explosion", "Lightning", "Ink Splat", "Knockback", "Heal 1 heart",
			"No effects for 20 seconds", "Give iron nugget", "Strength effect", "Tony time"
	);

	/**
	 * Method to summon any amount of entities with an optional target and set of potion effects
	 * @param player The player whose position to spawn the entities at
	 * @param level The instance ofLevel to which the player belongs
	 * @param e The EntityType to create
	 * @param hostile Whether the mob is a neutral mob that needs to be set hostile to the player
	 * @param count The amount of mobs to spawn
	 * @param effect The optional potion effect to spawn with
	 * @param strength The strength of the effect
	 * @param stacks The optional list of ItemStacks to be equipped with
	 */
	public static void summonEntity(Player player, Level level, EntityType<? extends Entity> e, boolean hostile, int count,
									MobEffect effect, int strength, ItemStack[] stacks) {
		summonEntityOffset(player, level, e, hostile, count, effect, strength, stacks, 0);
	}

	/**
	 * Method to summon any amount of entities with an optional target and set of potion effects at a random offset
	 * @param player The player whose position to spawn the entities at
	 * @param level The instance ofLevel to which the player belongs
	 * @param e The EntityType to create
	 * @param hostile Whether the mob is a neutral mob that needs to be set hostile to the player
	 * @param count The amount of mobs to spawn
	 * @param effect The optional potion effect to spawn with
	 * @param strength The strength of the effect
	 * @param stacks The optional list of ItemStacks to be equipped with
	 * @param offset The max offset to spawn at
	 */
	public static void summonEntityOffset(Player player, Level level, EntityType<? extends Entity> e,
										  boolean hostile, int count, MobEffect effect, int strength,
										  ItemStack[] stacks, int offset) {
		for(int i = 0; i < count; i++) {
			Entity entity = e.create(level);

			if(entity != null) {
				entity.setPos(player.position().add(randomOffset(offset)));

				// Call setTarget if applicable
				if(hostile) {
					if (entity instanceof Mob mob)
						mob.setTarget(player);
					if(entity instanceof NeutralMob mob)
						mob.setTarget(player);
				}
				// Spawn with potion effects if applicable
				if(effect != null && entity instanceof LivingEntity ent)
					ent.addEffect(new MobEffectInstance(effect, Integer.MAX_VALUE, strength));
				// Equip with items if applicable
				if(stacks != null && entity instanceof Monster monster) {
					for (ItemStack stack : stacks)
						monster.equipItemIfPossible(stack);
				}
				// Add a custom NBT tag to prevent mobs from dropping anything
				entity.getPersistentData().putBoolean("dropless", true);
				level.addFreshEntity(entity);
			}
		}
	}

	/**
	 * Returns a Vec3 with a random offset in the x and z directions
	 * @param offset the max offset
	 * @return a Vec3 with the random offset applied
	 */
	public static Vec3 randomOffset(int offset) {
		if (offset == 0) return new Vec3(0,0,0);
		int x = rand.nextInt(offset);
		int z = rand.nextInt(offset);
		int xFac = rand.nextDouble() < 0.5 ? -1 : 1;
		int zFac = rand.nextDouble() < 0.5 ? -1 : 1;

		return new Vec3(x * xFac, 0, z * zFac);
	}

	/**
	 * Clears a space above the player's head so tall mobs don't suffocate
	 * @param player the player to clear the blocks above
	 * @param level the server level instance
	 */
	public static void clearBlocksAbove(Player player, Level level) {
		BlockPos pos = player.blockPosition();
		for(int i = 0; i < 4; i ++) {
			level.setBlock(pos.north().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.west().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.south().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.east().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.north().east().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.south().east().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.north().west().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.south().west().offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
			level.setBlock(pos.offset(0, i, 0), Blocks.AIR.defaultBlockState(), 2);
		}
	}

	/**
	 * Sets a BlockPos to air if it's not Bedrock
	 * @param pos the blockpos to set to air
	 * @param level the serverlevel instance
	 */
	public static void clearIfNotBedrock(BlockPos pos, ServerLevel level) {
		if(!level.getBlockState(pos).is(Blocks.BEDROCK))
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
	}

	/**
	 * Removes a random amount of a random, non-empty item in the player's inventory
	 * @param player the player to remove items from
	 */
	public static void removeRandomItem(ServerPlayer player) {
		boolean empty = true;
		for(ItemStack stack : player.getInventory().items) {
			if(!stack.isEmpty())
				empty = false;
		}
		if (empty) return;

		int iters = 0;
		int slotId = rand.nextInt(9);

		while(player.getInventory().getItem(slotId).equals(ItemStack.EMPTY) && iters < 9) {
			slotId = rand.nextInt(9);
			System.out.println(slotId);
			iters++;
		}

		if(player.getInventory().getItem(slotId).getItem().equals(Items.AIR)) {
			for(int i = 0; i < 10; i++) {
				slotId = i;
				if(!player.getInventory().getItem(slotId).getItem().equals(Items.AIR))
					break;
			}
		}

		int slotCount = player.getInventory().getItem(slotId).getCount() + 1;
		int c = rand.nextInt(slotCount) + 1;

		System.out.println("Removing " + c + " of item " + player.getInventory().getItem(slotId));
		player.getInventory().removeItem(slotId, c);
	}

	/**
	 * Given a string in the form 'STATS response TIMES count', display a chat message in the form
	 * 'You said TRIGGER COUNT times'
	 * @param player the player to send the message to
	 * @param stat the string in the above format
	 */
	public static void displayStat(Player player, String stat) {
		int idx = -1;
		for (String resp : RESPONSES) {
			if(stat.contains(resp)) {
				idx = RESPONSES.indexOf(resp);
				break;
			}
		}
		if (idx == -1) return;

		String word = TRIGGERS.get(idx);
		String cnt = String.valueOf(Arrays.copyOfRange(stat.toCharArray(), stat.indexOf("TIMES") + 6, stat.length()));

		player.sendSystemMessage(Component.literal("You said '" + word + "' " + cnt + " times"));
	}

	/**
	 * Gives a player an item, places it in world if inventory is full
	 * @param player the player to give the item
	 * @param item the item to give
	 * @param count the amount of the item to give
	 */
	public static void giveItem(Player player, Item item, int count) {
		if(!player.getInventory().add(new ItemStack(item, count))) {
			ItemEntity itementity = new ItemEntity(player.getLevel(), player.getX(), player.getY(), player.getZ(),
					new ItemStack(item, count).copy());
			itementity.setDefaultPickUpDelay();
			player.getLevel().addFreshEntity(itementity);
		}
	}
}
