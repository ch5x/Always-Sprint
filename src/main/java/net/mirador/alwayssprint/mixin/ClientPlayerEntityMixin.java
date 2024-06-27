package net.mirador.alwayssprint.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
//Defines the targeted Mixin
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
	//Constructor matching ClientPlayerEntity
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}
	//Shadowing protected client Object
	@Shadow @Final
	protected MinecraftClient client;
	//Injecting at the Tail of the tickMovement() method
	@Inject(method = "tickMovement", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void tickMovement(CallbackInfo ci) {
		//set the Player to Sprinting if the forwardKey is pressed
		this.setSprinting(this.client.options.forwardKey.isPressed());
	}
}