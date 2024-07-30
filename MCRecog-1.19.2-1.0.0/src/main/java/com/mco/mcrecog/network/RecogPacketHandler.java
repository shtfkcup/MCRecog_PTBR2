package com.mco.mcrecog.network;

import com.mco.mcrecog.MCRecog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class RecogPacketHandler {
	private RecogPacketHandler() {}

	private static final String PROTOCOL_VERSION = "1";

	private static SimpleChannel INSTANCE;

	public static void init() {
		INSTANCE = NetworkRegistry.newSimpleChannel(
				new ResourceLocation(MCRecog.MODID, "main"),
				() -> PROTOCOL_VERSION,
				PROTOCOL_VERSION::equals,
				PROTOCOL_VERSION::equals);

		int index = 0;

		// Client -> Server
		INSTANCE.registerMessage(index++, ServerboundKeyUpdatePacket.class, ServerboundKeyUpdatePacket::encode,
				ServerboundKeyUpdatePacket::new, ServerboundKeyUpdatePacket::handle);
		// Server -> Client
		INSTANCE.registerMessage(index++, WordTimersDataSyncPacket.class, WordTimersDataSyncPacket::toBytes,
				WordTimersDataSyncPacket::new, WordTimersDataSyncPacket::handle);
		INSTANCE.registerMessage(index++, DeathDataSyncPacket.class, DeathDataSyncPacket::toBytes,
				DeathDataSyncPacket::new, DeathDataSyncPacket::handle);
		INSTANCE.registerMessage(index++, GraphicsTimersDataSyncPacket.class, GraphicsTimersDataSyncPacket::toBytes,
				GraphicsTimersDataSyncPacket::new, GraphicsTimersDataSyncPacket::handle);
	}

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}

	public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
		INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}
}
