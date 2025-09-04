@file:Suppress("unused", "DEPRECATION")

package btpos.mcmods.cutscenelib.neoforge.datagen.models

import btpos.mcmods.devutil.common.ext.java.invoke
import btpos.mcmods.cutscenelib.CutsceneLibMod.MODID
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING
import net.minecraft.client.data.models.BlockModelGenerators.plainVariant
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.ModelProvider
import net.minecraft.client.data.models.MultiVariant
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator
import net.minecraft.client.data.models.blockstates.PropertyDispatch
import net.minecraft.client.data.models.model.ModelTemplate
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.client.data.models.model.ModelTemplates.*
import net.minecraft.client.data.models.model.TextureMapping
import net.minecraft.client.data.models.model.TextureSlot
import net.minecraft.client.data.models.model.TextureSlot.BOTTOM
import net.minecraft.client.data.models.model.TextureSlot.SIDE
import net.minecraft.client.data.models.model.TextureSlot.TOP
import net.minecraft.client.data.models.model.TexturedModel
import net.minecraft.client.renderer.block.model.VariantMutator
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property

internal class ModelDataGen(output: PackOutput) : ModelProvider(output, MODID) {
	override fun registerModels(blockModels: BlockModelGenerators, itemModels: ItemModelGenerators) {
		blockModels.registerBlockModels()
		itemModels.registerItemModels()
	}
	
	private fun ItemModelGenerators.registerItemModels() {
	
	}
	
	private fun BlockModelGenerators.registerBlockModels() {
	
	}
	
	private fun BlockModelGenerators.registerRelocator() {
		val base = "relocator/relocator".texture().nocommit()
		val (top_on, top_off) = base.top.run {
			active.blockLoc() to inactive.blockLoc()
		}
		val (side_on, side_off) = base.side.run {
			active.blockLoc() to inactive.blockLoc()
		}
		val bottom = base.bottom.blockLoc()
		
		val off = CUBE_BOTTOM_TOP.create(blockLoc("relocator_off"), TextureMapping().apply {
			TOP(top_off)
			SIDE(side_off)
			BOTTOM(bottom)
		})
		val on = CUBE_BOTTOM_TOP.create(blockLoc("relocator"), TextureMapping().apply {
			TOP(top_on)
			SIDE(side_on)
			BOTTOM(bottom)
		})
		
		MultiVariantGenerator.dispatch(TerminusBlocks.ACTOR_RELOCATOR)
			.with(PropertyDispatch.initial(BlockRelocator.ACTIVE).trueFalse(on, off))
			.with(ROTATIONS_COLUMN_WITH_FACING)
			.submit()
	}
	
	
	/*
	//region Block Models
	private fun BlockModelGenerators.makeDungeonNexus() {
		createTrivialCube(ModBlocks_Builder.DUNGEON_NEXUS)
	}
	
	private fun BlockModelGenerators.makeTriggerBlock() {
		val TEXTURE_TOP_BOTTOM = "logic_programmer_top"
		val TEXTURE_SIDES = "logic_programmer_side"
		val TEXTURE_TOP_BOTTOM_ON = "logic_programmer_top_on"
		val TEXTURE_SIDES_ON = "logic_programmer_side_on"
		
		val off = columnModel(BlockTriggerHolder.id.powered(false), blockLoc(TEXTURE_SIDES), blockLoc(TEXTURE_TOP_BOTTOM))
		val on = columnModel(BlockTriggerHolder.id.powered(true), blockLoc(TEXTURE_SIDES_ON), blockLoc(TEXTURE_TOP_BOTTOM_ON))
		
		MultiVariantGenerator.dispatch(ModBlocks_Builder.TRIGGER_BLOCK)
			.with(poweredInitialVariants(off, on))
			.submit()
		
		registerSimpleItemModel(ModBlocks_Builder.TRIGGER_BLOCK, off)
	}
	
	private fun BlockModelGenerators.makeFightController() {
		val id = BlockFightController.id
		val t_off = blockLoc("fight_controller/${id}_inactive")
		val out_off = blockLoc("fight_controller/${id}_inactive_out")
		
		val t_ip = blockLoc("fight_controller/${id}_ip")
		val out_ip = blockLoc("fight_controller/${id}_ip_out")
		
		val t_com = blockLoc("fight_controller/${id}_complete")
		val out_com = blockLoc("fight_controller/${id}_complete_out")
		
		val off = sidedBlock(id + "_inactive", t_off, t_off, out_off, t_off, t_off, t_off)
		val com = sidedBlock(id + "_complete", t_com, t_com, out_com, t_com, t_com, t_com)
		val in_progress = sidedBlock(id + "_ip", t_ip, t_ip, out_ip, t_ip, t_ip, t_ip)
		
		MultiVariantGenerator.dispatch(ModBlocks_Builder.FIGHT_CONTROLLER)
			.with(
					BlockFightController.STATUS(
							FightStatus.INACTIVE to plainVariant(off),
							FightStatus.IN_PROGRESS to plainVariant(in_progress),
							FightStatus.COMPLETE to plainVariant(com)
			)).with(ROTATION_HORIZONTAL_FACING).submit()
		
		registerSimpleItemModel(ModBlocks_Builder.FIGHT_CONTROLLER, off)
	}
	
	private fun BlockModelGenerators.makeFlagReader() {
		val off = sidedBlock(BlockFlagReader.id.powered(false), rest=blockLoc("flag/${BlockFlagReader.id.powered(false)}"))
		val on = sidedBlock(BlockFlagReader.id.powered(true), rest=blockLoc("flag/${BlockFlagReader.id.powered(true)}"))
		
		MultiVariantGenerator.dispatch(ModBlocks_Builder.FLAG_READER)
			.with(poweredInitialVariants(off, on))
			.submit()
		
		registerSimpleItemModel(ModBlocks_Builder.FLAG_READER, off)
	}
	
	@Suppress("DuplicatedCode")
	private object FlagWriters {
		fun getTexture(id: String, powered: Boolean, input: Boolean = false): ResourceLocation {
			return blockLoc("flag/$id".powered(powered) + (if (input) "_input" else ""))
		}
		
		
		fun BlockModelGenerators.forSetter() {
			val setterLoc = BlockFlagWriter.id_setter
			
			val setter_off = sidedBlock(setterLoc.powered(false), north=getTexture(setterLoc, false, input=true), rest=getTexture(setterLoc, false))
			
			MultiVariantGenerator.dispatch(ModBlocks_Builder.FLAG_SETTER, plainVariant(setter_off))
				.with(ROTATION_HORIZONTAL_FACING)
				.submit()
			
			registerSimpleItemModel(ModBlocks_Builder.FLAG_SETTER, setter_off)
		}

		fun BlockModelGenerators.forResetter() {
			val setterLoc = BlockFlagWriter.id_resetter
			
			val setter_off = sidedBlock(setterLoc.powered(false), north=getTexture(setterLoc, false, input=true), rest=getTexture(setterLoc, false))
			
			MultiVariantGenerator.dispatch(ModBlocks_Builder.FLAG_RESETTER, plainVariant(setter_off))
				.with(ROTATION_HORIZONTAL_FACING)
				.submit()
			
			registerSimpleItemModel(ModBlocks_Builder.FLAG_RESETTER, setter_off)
		}
	}
	
	private object WirelessRedstone {
		const val txBase = "redstone/"
		
		fun BlockModelGenerators.forTransmitter() {
			val txSpecific = txBase + "transmitter/transmitter"
			val off = columnModel(BlockRedstoneTransmitter.Companion.id.powered(false), blockLoc("${txSpecific}_sides").powered(false), blockLoc("${txSpecific}_top").powered(false))
			val on = columnModel(BlockRedstoneTransmitter.Companion.id.powered(true), blockLoc("${txSpecific}_sides").powered(true), blockLoc("${txSpecific}_top").powered(true))
			
			MultiVariantGenerator.dispatch(ModBlocks_Builder.REDSTONE_TRANSMITTER)
				.with(poweredInitialVariants(off, on))
				.submit()
			
			registerSimpleItemModel(ModBlocks_Builder.REDSTONE_TRANSMITTER, off)
		}
		
		fun BlockModelGenerators.forReceiver() {
			val txSpecific = txBase + "receiver/receiver"
			val off = columnModel(BlockRedstoneReceiver.Companion.id.powered(false), blockLoc("${txSpecific}_sides").powered(false), blockLoc("${txSpecific}_top").powered(false))
			val on = columnModel(BlockRedstoneReceiver.Companion.id.powered(true), blockLoc("${txSpecific}_sides").powered(true), blockLoc("${txSpecific}_top").powered(true))
			
			MultiVariantGenerator.dispatch(ModBlocks_Builder.REDSTONE_RECEIVER)
				.with(poweredInitialVariants(off, on))
				.submit()
			
			
			registerSimpleItemModel(ModBlocks_Builder.REDSTONE_RECEIVER, off)
		}
	}
	//endregion
	
	*/
}

/**
 * A template for a horizontal column with a custom front and back, but the same faces always on the top and bottom.
 */
val OBSERVER_LIKE_BLOCK: ModelTemplate = ModelTemplates.create("btpos_devutil:template_observer_like", TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT, TextureSlot.TOP, TextureSlot.PARTICLE)

private fun blockLoc(path: String): ResourceLocation {
	return ResourceLocation.fromNamespaceAndPath(MODID, "block/$path")
}

//region Macros
context(gen: BlockModelGenerators)
private fun ModelTemplate.create(id: String, textureMapping: TextureMapping): ResourceLocation {
	return this.create(blockLoc(id), textureMapping, gen.modelOutput)
}

private fun PropertyDispatch.C1<MultiVariant, Boolean>.trueFalse(whenTrue: ResourceLocation, whenFalse: ResourceLocation) = this.select(true, plainVariant(whenTrue)).select(false, plainVariant(whenFalse))


context(mapping: TextureMapping)
operator fun TextureSlot.invoke(loc: ResourceLocation) {
	mapping.put(this, loc)
}

context(gen: BlockModelGenerators) fun ModelTemplate.create(modelLocation: ResourceLocation, textureMapping: TextureMapping) = create(modelLocation, textureMapping, gen.modelOutput)
context(gen: BlockModelGenerators) fun ModelTemplate.create(block: Block, textureMapping: TextureMapping) = create(block, textureMapping, gen.modelOutput)
context(gen: BlockModelGenerators) fun ModelTemplate.create(item: Item, textureMapping: TextureMapping) = create(item, textureMapping, gen.modelOutput)

private fun TextureMapping.blockFaces(top: ResourceLocation, bottom: ResourceLocation, north: ResourceLocation, south: ResourceLocation, east: ResourceLocation, west: ResourceLocation): TextureMapping {
	return this.put(TextureSlot.UP, top)
		.put(TextureSlot.DOWN, bottom)
		.put(TextureSlot.NORTH, north)
		.put(TextureSlot.EAST, east)
		.put(TextureSlot.SOUTH, south)
		.put(TextureSlot.WEST, west)
}


//region Model Instantiation
context(_: BlockModelGenerators)
private fun sidedBlock(id: String, top: ResourceLocation, bottom: ResourceLocation, north: ResourceLocation, south: ResourceLocation, east: ResourceLocation, west: ResourceLocation, particle: ResourceLocation = south): ResourceLocation {
	return ModelTemplates.CUBE.create(id, TextureMapping().blockFaces(top, bottom, north, south, east, west).put(TextureSlot.PARTICLE, particle))
}

/**
 * For use with named arguments.
 *
 * `sidedBlock("name", top=myTopTxt, north=outputTxt, rest=otherTxt)`
 */
context(_: BlockModelGenerators)
private fun sidedBlock(id: String, rest: ResourceLocation, top: ResourceLocation = rest, bottom: ResourceLocation = rest, north: ResourceLocation = rest, south: ResourceLocation = rest, east: ResourceLocation = rest, west: ResourceLocation = rest, particle: ResourceLocation = rest): ResourceLocation {
	return sidedBlock(id, top, bottom, north, south, east, west, particle)
}

private fun BlockModelGenerators.columnModel(id: String, side: ResourceLocation, updown: ResourceLocation): ResourceLocation {
	return ModelTemplates.CUBE_COLUMN.create(blockLoc(id), TextureMapping.column(side, updown), modelOutput)
}



//region Variant Building
private fun poweredInitialVariants(off: ResourceLocation, on: ResourceLocation): PropertyDispatch.C1<MultiVariant?, Boolean> {
	return PropertyDispatch.initial(BlockStateProperties.POWERED)
		.select(true, plainVariant(on))
		.select(false, plainVariant(off))
}

private operator fun <T : Comparable<T>> Property<T>.invoke(vararg pairs: Pair<T, MultiVariant>): PropertyDispatch.C1<MultiVariant, T> {
	val it = PropertyDispatch.initial(this)
	var current = it.select(pairs[0].first, pairs[0].second)
	for (i in 1..<pairs.size) {
		current = current.select(pairs[i].first, pairs[i].second)
	}
	return current
}

@JvmName("invokeMutator")
private operator fun <T : Comparable<T>> Property<T>.invoke(vararg pairs: Pair<T, VariantMutator>): PropertyDispatch.C1<VariantMutator, T> {
	val it = PropertyDispatch.modify(this)
	var current = it.select(pairs[0].first, pairs[0].second)
	for (i in 1..<pairs.size) {
		current = current.select(pairs[i].first, pairs[i].second)
	}
	return current
}

context(reg: BlockModelGenerators)
private fun MultiVariantGenerator.submit() {
	reg.blockStateOutput(this)
}

fun ItemModelGenerators.basicItem(item: Item) = this.generateFlatItem(item, ModelTemplates.FLAT_ITEM)

/**
 * Enforces a strict order for texture names:
 * `texture("myblock").powered.bottom.active -> "myblock_bottom_active_powered"`
 */
private fun String.texture() = TexturePathBuilder(this)
/**
 * Enforces a strict order for texture names:
 * `texture("myblock").powered.active.bottom -> "myblock_bottom_active_powered"`
 */
private class TexturePathBuilder private constructor(private val startingPath: String, private val attributes: MutableMap<Attribute, String>) {
	constructor(startingPath: String) : this(startingPath, Object2ObjectArrayMap())
	
	fun copy() = TexturePathBuilder(startingPath, Object2ObjectArrayMap(attributes))
	
	/**
	 * We don't own these assets, so they should NOT make it into the final build! (or git)
	 */
	@Deprecated("Textures marked with this should NOT make it into the final build!")
	fun nocommit() = TexturePathBuilder("nocommit/$startingPath", Object2ObjectArrayMap(attributes))
	
	/**
	 * The order of the items in this are the order the modifiers will be in the texture,
	 */
	private enum class Attribute {
		SIDE,
		IO,
		ACTIVE,
		REDSTONE
	}
	
	private fun with(attribute: Attribute, value: String) = copy().apply {
		attributes[attribute] = value
	}
	
	fun side(side: String) = with(Attribute.SIDE, side)
	
	fun isPowered(isPowered: Boolean) = with(Attribute.REDSTONE, if (isPowered) "powered" else "unpowered")
	
	fun isActive(isActive: Boolean) = with(Attribute.ACTIVE, if (isActive) "active" else "inactive")
	
	val top
		get() = side("top")
	val bottom
		get() = side("bottom")
	val side
		get() = side("side")
	val front
		get() = side("front")
	val back
		get() = side("back")
	
	val powered_unpowered
		get() = powered to unpowered
	val powered
		get() = isPowered(true)
	val unpowered
		get() = isPowered(false)
	
	
	val active_inactive
		get() = active to inactive
	val active
		get() = isActive(true)
	val inactive
		get() = isActive(false)
	
	
	val input
		get() = with(Attribute.IO, "input")
	val output
		get() = with(Attribute.IO, "output")
	
	val attributesString
		get() = Attribute.entries.mapNotNull { attributes[it] }.joinToString("_")
	
	val fullFileName
		get() = startingPath + "_" + attributesString
	
	fun blockLoc() = ResourceLocation.fromNamespaceAndPath(MODID, "block/$fullFileName")
	fun itemLoc() = ResourceLocation.fromNamespaceAndPath(MODID, "item/$fullFileName")
}