package ch.epfl.sweng.testing.part4;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private List<Entity> entities;

    public Environment() {
        this.entities = new ArrayList<>();
    }

    /**
     * Add a new entity to the environment
     *
     * @param position the position where the entity should be placed
     * @param entityType the type of entity to add
     *
     * @throws IllegalArgumentException if the position is invalid, i.e. either its x or y is negative
     *                                  or an entity is already at that position
     *
     *
     */
    public void addEntity(Position position, EntityType entityType) {
        List<Entity> entities = new ArrayList<>(this.entities);

        boolean validPlacement = true;

        for(Entity e : entities) {
            if (e.getPosition() == position) {
                validPlacement = false;
            }
        }

        if (!validPlacement) {
            throw new IllegalArgumentException("Placement is not valid : An entity is already there!");
        } else {
            switch(entityType) {
                case PANDA:
                    Panda panda = new Panda(position);
                    entities.add(panda);

                case BAMBOO:
                    Bamboo bamboo = new Bamboo(position);
                    entities.add(bamboo);
            }

        }


    }

    /**
     * Updates all the entities in the environment, if they can grow
     */
    public void growthCycle() {
        for(Entity e : entities) {
            if (!e.canGrow()) {
                e.update();
            }
        }
    }

}
