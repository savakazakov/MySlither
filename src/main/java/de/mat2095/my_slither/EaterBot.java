package de.mat2095.my_slither;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class EaterBot extends Player {

    private final MapPoint aim;

    private EaterBot() {
        super("EaterBot");
        aim = new MapPoint(32, new Color(0xA000FF00, true));
        mapPoints.add(aim);
    }

    //Where is this used?
    public static List<Player> getPlayers() {
        List<Player> result = new LinkedList<>();
        result.add(new EaterBot());
        return result;
    }

    private static double smoothify(double value) {
        return Math.cos(value * Math.PI) / 2 + 0.5;
    }

    //Where is this used?
    private void addFoodMapPoint(Food food, int color, double alpha) {
        MapPoint mapPoint = new MapPoint(16, new Color(color | (int) (alpha * 0x7F) << 24, true));
        mapPoint.x = food.x;
        mapPoint.y = food.y;
        mapPoint.visible = true;
        mapPoints.add(mapPoint);
    }

    @Override
    public Wish action(MySlitherModel model) { // TODO: don't use model directly
        mapPoints.clear();
        mapPoints.add(aim);

        double sumX = 0;
        double sumY = 0;
        double sumWeight = 0;

        Snake snake = model.snake;

        if (snake != null) {
            for (Food food : model.foods.values()) {

                /*  TODO:
                 *  - fix shaking?
                 *  - ignore unreachable food directly behind snake
                 *  - correlation distance/direction (great distance => direction not important)
                 *  - ignore food blocked by enemies
                 *  - given two piles, chose one of them, not the middle
                 */
                
                double sizeWeight = 128 / (128 + Math.pow(food.getRadius(), 1.5));
                sizeWeight = sizeWeight * sizeWeight;
                sizeWeight = smoothify(sizeWeight);

                //distance goes up distanceWeight goes up! 
                double distance = Math.sqrt((food.x - snake.x) * (food.x - snake.x) + (food.y - snake.y) * (food.y - snake.y));
                double distanceWeight = 1 - model.sectorSize / (model.sectorSize + distance);
                distanceWeight = smoothify(distanceWeight);

                //angle in rads between the positive x-axis and a point (x,y)
                double angle = Math.atan2(food.y - snake.y, food.x - snake.x);
                double deltaAngle = angle - snake.ang;
                if (deltaAngle < -Math.PI) {
                    deltaAngle += MySlitherModel.PI2;
                }
                double directionWeight = Math.abs(deltaAngle) / Math.PI;
                //Fix the distance/direction correlation.
                directionWeight *= (1 - Math.pow(distanceWeight, 5));//
                directionWeight = smoothify(directionWeight);
                directionWeight = 0.9 * Math.pow(directionWeight, 4) + 0.1;
                if (distance < model.sectorSize && directionWeight < 0.25) {
                    directionWeight = 0;
                }

                double weight = sizeWeight * distanceWeight * directionWeight;

                //Visualizing the importance of foods.
                addFoodMapPoint(food, 0xFF8000, sizeWeight);
                addFoodMapPoint(food, 0x00FF80, distanceWeight);
                addFoodMapPoint(food, 0x8000FF, directionWeight);

                //x and y are negative depending on the position of the player.
                //I.e. the centre of the screen is (0,0).
                sumX += food.x * weight;
                sumY += food.y * weight;
                sumWeight += weight;
            }
        }

        if (sumWeight > 0) 
        {
            //We have our sumX, sumY and sumWeight
            //Now check the distance b/n the aim and the snake.
            double foodCentreX = sumX / sumWeight;
            double foodCentreY = sumY / sumWeight;
            double distanceFoodCentre = Math.sqrt((foodCentreX - snake.x) * (foodCentreX - snake.x) + (foodCentreY - snake.y) * (foodCentreY - snake.y));

            //If the distance to the centre of the food mass is less than the sector size,
            //the snake should aim to eat the nearest foods.
            //And not run to just a slightly better region.
            if(distanceFoodCentre < model.sectorSize * 3)
            {
                //Initialize to arbitrarily big values.
                Food closestFood = null;
                double closestDistance = 1000;

                //Aim for the nearest food in a 180-degree sector in front of the snake.
                for (Food food : model.foods.values()) 
                {
                    //First check the food is in the right sector and then look for the closest one.
                    //Angle in rads between the positive x-axis and a point (x,y)
                    double angle = Math.atan2(food.y - snake.y, food.x - snake.x);
                    double deltaAngle = angle - snake.ang;
                    if (deltaAngle < -Math.PI)
                        deltaAngle += MySlitherModel.PI2;
                    deltaAngle = Math.abs(deltaAngle);
                    if(deltaAngle < Math.PI / 2)
                    {
                        double distance = Math.sqrt((food.x - snake.x) * (food.x - snake.x) + (food.y - snake.y) * (food.y - snake.y));
                        if(distance < closestDistance) 
                        {
                            closestDistance = distance;
                            closestFood = food;
                        }    
                    }
                }

                //Set to true for visual guidance.
                aim.visible = true;
                aim.x = closestFood.x;
                aim.y = closestFood.y;
                return new Wish((Math.atan2(aim.y - snake.y, aim.x - snake.x) + MySlitherModel.PI2) % MySlitherModel.PI2, false);
            }
            else
            {
                aim.visible = true;
                aim.x = foodCentreX;
                aim.y = foodCentreY;
                return new Wish((Math.atan2(aim.y - snake.y, aim.x - snake.x) + MySlitherModel.PI2) % MySlitherModel.PI2, false);
            }
        } 
        else 
        {
            aim.visible = false;
            return new Wish(null, false);
        }
    }
}
