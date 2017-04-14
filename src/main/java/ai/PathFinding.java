package ai;

import map.MapPoint;
import utils.Tuple3;

import java.util.*;

/**
 * A class to find a path between two points.
 */
public class PathFinding {

    /**
     * A simple point.
     */
    public static class Point {
        private int x; // abscissa.
        private int y; // ordinate.

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    /**
     * This function returns a set containing the direct neighbors (pathway & mutable points only) of a point.
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param point          the point
     * @return a set containing the direct neighbors (pathway & mutable points only)
     */
    public static Set<Point> getNeighbors(MapPoint[][] mapPointMatrix,
                                          int mapWidth,
                                          int mapHeight,
                                          Point point) {
        Set<Point> res = new HashSet<>();
        if ((point.getX() - 1 >= 0) &&
                (mapPointMatrix[point.getY()][point.getX() - 1].isPathway() ||
                        mapPointMatrix[point.getY()][point.getX() - 1].isMutable())) {
            res.add(new Point(point.getX() - 1, point.getY()));
        }
        if ((point.getX() + 1 < mapWidth) &&
                (mapPointMatrix[point.getY()][point.getX() + 1].isPathway() ||
                        mapPointMatrix[point.getY()][point.getX() + 1].isMutable())) {
            res.add(new Point(point.getX() + 1, point.getY()));
        }
        if ((point.getY() - 1 >= 0) &&
                (mapPointMatrix[point.getY() - 1][point.getX()].isPathway() ||
                        mapPointMatrix[point.getY() - 1][point.getX()].isMutable())) {
            res.add(new Point(point.getX(), point.getY() - 1));
        }
        if ((point.getY() + 1 < mapHeight) &&
                (mapPointMatrix[point.getY() + 1][point.getX()].isPathway() ||
                        mapPointMatrix[point.getY() + 1][point.getX()].isMutable())) {
            res.add(new Point(point.getX(), point.getY() + 1));
        }
        return res;
    }

    /**
     * The function computes the square of the euclidean distance between two points.
     *
     * @param firstPoint  the 1st point
     * @param secondPoint the 2nd point
     * @return the square of the euclidean distance between the two points.
     */
    public static int computeDistance(Point firstPoint, Point secondPoint) {
        return (firstPoint.getY() - secondPoint.getY()) * (firstPoint.getY() - secondPoint.getY()) +
                (firstPoint.getX() - secondPoint.getX()) * (firstPoint.getX() - secondPoint.getX());
    }

    /**
     * The function returns the less cost fScore point of a list.
     *
     * @param list   the list of point
     * @param scores the map of point costs
     * @return the less cost fScore point of a list.
     */
    public static Point getTheLessCostFScorePoint(LinkedList<Point> list,
                                                  Map<Point, Tuple3<Integer, Integer, Point>> scores) {
        Point res = list.getFirst();
        int lowestFScore = scores.get(res).getSecond();
        for (Point point : list) {
            if (scores.get(point).getSecond() < lowestFScore) {
                lowestFScore = scores.get(point).getSecond();
                res = point;
            }
        }
        return res;
    }

    /**
     * Look for a path between two points.
     * The used algorithm is A* (https://en.wikipedia.org/wiki/A*_search_algorithm)
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param origin         the origin point
     * @param destination    the destination point
     * @return true if there exit a path between the two points, false otherwise.
     */
    public static boolean isThereAPathBetweenTwoPoints(MapPoint[][] mapPointMatrix,
                                                       int mapWidth,
                                                       int mapHeight,
                                                       Point origin,
                                                       Point destination) {
        LinkedList<Point> closedSet = new LinkedList<>();
        LinkedList<Point> openedSet = new LinkedList<>();

        /*
          Map with:
          - key as a Point
          - value as:
            - gScore (Integer)
            - fScore (Integer)
            - parent's Point
         */
        Map<Point, Tuple3<Integer, Integer, Point>> scores = new HashMap<>();

        openedSet.add(origin);
        scores.put(origin, new Tuple3<>(0, computeDistance(origin, destination), null));
        while (!openedSet.isEmpty()) {
            Point curNode = getTheLessCostFScorePoint(openedSet, scores);
            if (curNode.equals(destination)) { // it is the end.
                return true;
            }
            openedSet.remove(curNode);
            closedSet.add(curNode);

            for (Point neighbor : getNeighbors(mapPointMatrix, mapWidth, mapHeight, curNode)) {
                if (closedSet.contains(neighbor)) {
                    continue; // ignore already evaluated neighbor.
                }
                int newGScore = scores.get(curNode).getFirst() + computeDistance(curNode, neighbor);
                if (!openedSet.contains(neighbor)) {
                    openedSet.add(neighbor);
                    scores.put(neighbor,
                            new Tuple3<>(newGScore, newGScore + computeDistance(neighbor, destination), curNode));
                } else {
                    if (newGScore < scores.get(neighbor).getFirst()) {
                        scores.get(neighbor).setFirst(newGScore);
                        scores.get(neighbor).setSecond(newGScore + computeDistance(neighbor, destination));
                        scores.get(neighbor).setThird(curNode);
                    }
                }
            }
        }
        return false;
    }
}
