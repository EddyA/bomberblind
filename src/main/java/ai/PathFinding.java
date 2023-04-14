package ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import map.MapPoint;
import utils.Tuple2;

/**
 * A class to find a path between two points. The algorithm used is A* (<a
 * href="https://en.wikipedia.org/wiki/A">...</a>*_search_algorithm)
 */
@UtilityClass
public class PathFinding {

    /**
     * A simple point.
     *
     * @param x abscissa.
     * @param y ordinate.
     */
    public record Point(int x, int y) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    /**
     * This function returns a set of points including the direct neighbors of a point (pathways & mutable obstacles only).
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param point          the point
     * @return a set of points including the direct neighbors of the point (pathways & mutable obstacles only)
     */
    public static Set<Point> getNeighbors(MapPoint[][] mapPointMatrix,
                                          int mapWidth,
                                          int mapHeight,
                                          Point point) {
        Set<Point> res = new HashSet<>();
        if ((point.x() - 1 >= 0) &&
            (mapPointMatrix[point.y()][point.x() - 1].isPathway() ||
                mapPointMatrix[point.y()][point.x() - 1].isMutable())) {
            res.add(new Point(point.x() - 1, point.y()));
        }
        if ((point.x() + 1 < mapWidth) &&
            (mapPointMatrix[point.y()][point.x() + 1].isPathway() ||
                mapPointMatrix[point.y()][point.x() + 1].isMutable())) {
            res.add(new Point(point.x() + 1, point.y()));
        }
        if ((point.y() - 1 >= 0) &&
            (mapPointMatrix[point.y() - 1][point.x()].isPathway() ||
                mapPointMatrix[point.y() - 1][point.x()].isMutable())) {
            res.add(new Point(point.x(), point.y() - 1));
        }
        if ((point.y() + 1 < mapHeight) &&
            (mapPointMatrix[point.y() + 1][point.x()].isPathway() ||
                mapPointMatrix[point.y() + 1][point.x()].isMutable())) {
            res.add(new Point(point.x(), point.y() + 1));
        }
        return res;
    }

    /**
     * The function computes the square of the euclidean distance between two points.
     *
     * @param firstPoint  the 1st point
     * @param secondPoint the 2nd point
     * @return the square of the euclidean distance between the two points
     */
    public static int computeDistance(Point firstPoint, Point secondPoint) {
        return (firstPoint.y() - secondPoint.y()) * (firstPoint.y() - secondPoint.y()) +
            (firstPoint.x() - secondPoint.x()) * (firstPoint.x() - secondPoint.x());
    }

    /**
     * The function returns the point of a list having the lowest fScore.
     *
     * @param list   the list of point
     * @param scores the map of point scores
     * @return the point of a list having the lowest fScore
     */
    public static Point getPointWithLowestFScore(List<Point> list,
                                                 Map<Point, Tuple2<Integer, Integer>> scores) {
        Point res = list.get(0);
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
     * This function reconstructs the found path (as a set of points).
     *
     * @param destination the destination point
     * @param parents     the map of point parents
     * @return the path is found (as a set of points), false otherwise
     */
    public static Set<Point> reconstructFoundPath(Point destination,
                                                  Map<Point, Point> parents) {
        Set<Point> res = new HashSet<>();
        Point curPoint = destination;
        res.add(curPoint);
        while (curPoint != null) {
            curPoint = parents.get(curPoint);
            if (curPoint != null) {
                res.add(curPoint);
            }
        }
        return res;
    }

    /**
     * Look for a path between two points.
     * The algorithm used is A* (<a href="https://en.wikipedia.org/wiki/A">...</a>*_search_algorithm)
     *
     * @param mapPointMatrix the map (represented by its matrix of MapPoint)
     * @param mapWidth       the map width
     * @param mapHeight      the map height
     * @param origin         the 1st point
     * @param destination    the 2nd point
     * @return a Tuple2(true, set of points) if there exit a path between the two points, a tuple2(false, null) otherwise.
     */
    public static Tuple2<Boolean, Set<Point>> isThereAPathBetweenTwoPoints(MapPoint[][] mapPointMatrix,
                                                                           int mapWidth,
                                                                           int mapHeight,
                                                                           Point origin,
                                                                           Point destination) {
        LinkedList<Point> closedSet = new LinkedList<>();
        LinkedList<Point> openedSet = new LinkedList<>();

        Map<Point, Tuple2<Integer, Integer>> scores = new HashMap<>(); // Map<point, Tuple2<gScore, fScore>>
        Map<Point, Point> parents = new HashMap<>(); // Map<point, parent>

        openedSet.add(origin);
        scores.put(origin, new Tuple2<>(0, computeDistance(origin, destination)));
        while (!openedSet.isEmpty()) {
            Point curPoint = getPointWithLowestFScore(openedSet, scores);
            if (curPoint.equals(destination)) { // it is the end.
                return new Tuple2<>(true, PathFinding.reconstructFoundPath(destination, parents));
            }
            openedSet.remove(curPoint);
            closedSet.add(curPoint);

            for (Point neighbor : getNeighbors(mapPointMatrix, mapWidth, mapHeight, curPoint)) {
                if (closedSet.contains(neighbor)) {
                    continue; // ignore already evaluated neighbor.
                }
                int newGScore = scores.get(curPoint).getFirst() + computeDistance(curPoint, neighbor);
                if (!openedSet.contains(neighbor)) {
                    openedSet.add(neighbor);
                    scores.put(neighbor, new Tuple2<>(newGScore, newGScore + computeDistance(neighbor, destination)));
                    parents.put(neighbor, curPoint);
                } else {
                    if (newGScore < scores.get(neighbor).getFirst()) {
                        scores.get(neighbor).setFirst(newGScore);
                        scores.get(neighbor).setSecond(newGScore + computeDistance(neighbor, destination));
                        parents.put(neighbor, curPoint);
                    }
                }
            }
        }
        return new Tuple2<>(false, null);
    }
}
