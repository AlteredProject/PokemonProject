package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    private boolean isTileColliding(int[][] map, int col, int row) {
        int tileNum = map[col][row];
        return gp.tileM.tile[tileNum].collision;
    }


    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;

                boolean collidingBackground =
                        isTileColliding(gp.tileM.mapTileNumBackground, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumBackground, entityRightCol, entityTopRow);
                boolean collidingEnvironmentB =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityRightCol, entityTopRow);
                boolean collidingEnvironmentF =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityRightCol, entityTopRow);

                if(collidingBackground || collidingEnvironmentB || collidingEnvironmentF) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;

                boolean collidingBackground =
                        isTileColliding(gp.tileM.mapTileNumBackground, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumBackground, entityLeftCol, entityBottomRow);
                boolean collidingEnvironmentB =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityLeftCol, entityBottomRow);
                boolean collidingEnvironmentF =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityLeftCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityLeftCol, entityBottomRow);

                if(collidingBackground || collidingEnvironmentB || collidingEnvironmentF) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;

                boolean collidingBackground =
                        isTileColliding(gp.tileM.mapTileNumBackground, entityLeftCol, entityBottomRow) ||
                        isTileColliding(gp.tileM.mapTileNumBackground, entityRightCol, entityBottomRow);
                boolean collidingEnvironmentB =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityLeftCol, entityBottomRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityRightCol, entityBottomRow);
                boolean collidingEnvironmentF =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityLeftCol, entityBottomRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityRightCol, entityBottomRow);

                if(collidingBackground || collidingEnvironmentB || collidingEnvironmentF) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

                boolean collidingBackground =
                        isTileColliding(gp.tileM.mapTileNumBackground, entityRightCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumBackground, entityRightCol, entityBottomRow);
                boolean collidingEnvironmentB =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityRightCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentB, entityRightCol, entityBottomRow);
                boolean collidingEnvironmentF =
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityRightCol, entityTopRow) ||
                        isTileColliding(gp.tileM.mapTileNumEnvironmentF, entityRightCol, entityBottomRow);

                if(collidingBackground || collidingEnvironmentB || collidingEnvironmentF) {
                    entity.collisionOn = true;
                }
            }
        }
    }
}
