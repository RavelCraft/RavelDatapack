package com.connexal.raveldatapack.entities;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.types.entities.CustomEntity;
import com.github.imdabigboss.easydatapack.api.types.entities.model.EntityBone;
import com.github.imdabigboss.easydatapack.api.types.entities.model.EntityModel;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEntityException;
import org.bukkit.Material;

public class TestEntity {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws CustomEntityException {
        EntityModel model = EntityModel.builder(
                EntityBone.builder(0, Material.LIGHT_BLUE_WOOL) //BODY
                        //.pivot(0, 9, 0)
                        .rotation(-22.5f, 0, 0)
                        .offset(-4, 5, -4)
                        .addChild(EntityBone.builder(0, Material.YELLOW_WOOL) //HEAD
                                //.pivot(0, 13, 0)
                                .rotation(0, 0, -15)
                                .offset(-2, 13, -2)
                                .build())
                        .addChild(EntityBone.builder(0, Material.ORANGE_WOOL) //LEG0
                                //.pivot(2, 5, 0)
                                .offset(1, 0, -1)
                                .build())
                        .addChild(EntityBone.builder(0, Material.RED_WOOL) //LEG1
                                //.pivot(-2, 5, 0)
                                .offset(-3, 0, -1)
                                .build())
                        .addChild(EntityBone.builder(0, Material.BLUE_WOOL) //ARM0
                                //.pivot(4, 12, 0)
                                .rotation(0, 0, -22.5f)
                                .offset(4, 7, -1)
                                .build())
                        .addChild(EntityBone.builder(0, Material.GREEN_WOOL) //ARM1
                                //.pivot(-4, 12, 0)
                                .rotation(0, 0, 22.5f)
                                .offset(-6, 7, -1)
                                .build())
                        .build()
        ).build();

        CustomEntity entity = CustomEntity.builder("Test", "test", customModelData, model)
                .setHealth(10)
                .build();

        adder.register(entity);
    }
}
