// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class portal_gun<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "portal_gun"), "main");
	private final ModelPart portal_gun;
	private final ModelPart front;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart top_arm;

	public portal_gun(ModelPart root) {
		this.portal_gun = root.getChild("portal_gun");
		this.front = this.portal_gun.getChild("front");
		this.right_arm = this.front.getChild("right_arm");
		this.left_arm = this.front.getChild("left_arm");
		this.top_arm = this.front.getChild("top_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition portal_gun = partdefinition.addOrReplaceChild("portal_gun", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -2.5F, -3.5F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(12, 8).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 11).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 5.0F));

		PartDefinition cube_r1 = portal_gun.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 26).addBox(0.25F, 1.0F, -5.5F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.5F, -0.25F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r2 = portal_gun.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 26).mirror().addBox(-4.0F, 0.0F, -2.75F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -0.5F, -4.75F, 0.0F, 0.0F, 0.7854F));

		PartDefinition front = portal_gun.addOrReplaceChild("front", CubeListBuilder.create().texOffs(24, 10).addBox(-1.0F, -0.25F, -4.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 7).addBox(-1.0F, -0.25F, -4.75F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(-1.5F, -0.75F, -2.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 19).addBox(-2.0F, -0.25F, -3.75F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, -4.75F));

		PartDefinition right_arm = front.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(19, 14).mirror().addBox(-3.0F, 0.0F, -4.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 2.25F, -2.75F));

		PartDefinition left_arm = front.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(19, 14).addBox(-1.0F, 0.0F, -4.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.25F, -2.75F));

		PartDefinition top_arm = front.addOrReplaceChild("top_arm", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -3.0F, -4.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, -2.75F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		portal_gun.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}