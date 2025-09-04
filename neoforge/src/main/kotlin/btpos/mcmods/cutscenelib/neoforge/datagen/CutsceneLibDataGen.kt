@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package btpos.mcmods.cutscenelib.neoforge.datagen

import btpos.mcmods.cutscenelib.neoforge.datagen.lang.LangGen_English
import btpos.mcmods.cutscenelib.neoforge.datagen.models.ModelDataGen
import btpos.mcmods.cutscenelib.CutsceneLibMod.MODID
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid=MODID, value=[Dist.CLIENT])
internal object CutsceneLibDataGen {
	@SubscribeEvent
	fun gatherDataEvent(evt: GatherDataEvent.Client) {
		evt.createProvider(::ModelDataGen)
		evt.createProvider(::LangGen_English)
	}
}