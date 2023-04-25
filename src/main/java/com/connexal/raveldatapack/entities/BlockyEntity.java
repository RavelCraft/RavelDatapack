package com.connexal.raveldatapack.entities;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.types.entities.CustomEntity;
import com.github.imdabigboss.easydatapack.api.types.entities.model.EntityBone;
import com.github.imdabigboss.easydatapack.api.types.entities.model.EntityModel;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEntityException;
import org.bukkit.Material;

public class BlockyEntity {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws CustomEntityException {
        EntityModel model = EntityModel.builder(
                EntityBone.builder(0, Material.LIGHT_BLUE_WOOL) //BODY
                        //.pivot(0, 12, 0)
                        .rotation(-22.5f, 0, 0)
                        .offset(-4, 8, -4)
                        .addChild(EntityBone.builder(0, Material.YELLOW_WOOL) //HEAD
                                //.pivot(0, 16, 0)
                                .rotation(0, 0, -15)
                                .offset(-4, 16, -4)
                                .build())
                        .addChild(EntityBone.builder(0, Material.ORANGE_WOOL) //LEG0
                                //.pivot(4, 8, 0)
                                .offset(0, 0, -4)
                                .build())
                        .addChild(EntityBone.builder(0, Material.RED_WOOL) //LEG1
                                //.pivot(-4, 8, 0)
                                .offset(-8, 0, -4)
                                .build())
                        .addChild(EntityBone.builder(0, Material.MAGENTA_WOOL) //ARM0
                                //.pivot(4, 16, 0)
                                .rotation(0, 0, 22.5f)
                                .offset(4, 8, -4)
                                .build())
                        .addChild(EntityBone.builder(0, Material.BLUE_WOOL) //ARM1
                                //.pivot(-4, 16, 0)
                                .rotation(0, 0, -22.5f)
                                .offset(-12, 8, -4)
                                .addChild(EntityBone.builder(0, Material.PINK_WOOL)
                                        //.pivot(-8, 8, 0)
                                        .offset(-12, 0, -4)
                                        .build())
                                .build())
                        .build()
        ).build();

        CustomEntity entity = CustomEntity.builder("Blocky", "blocky", customModelData, model)
                .setHealth(20)
                .build();

        adder.register(entity);
    }
}
