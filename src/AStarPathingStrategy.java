//import java.util.*;
//import java.util.function.BiPredicate;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//class AStarPathingStrategy
//        implements PathingStrategy
//{
//
//
//    public List<Point> computePath(Point start, Point end,
//                                   Predicate<Point> canPassThrough,
//                                   BiPredicate<Point, Point> withinReach,
//                                   Function<Point, Stream<Point>> potentialNeighbors)
//    {
//        List<Point> path = new LinkedList<>();
//
//        Queue<Node> openListQ = new PriorityQueue<Node>(Comparator.comparing(Node::getF));
//        HashMap<Point, Node> openListM = new HashMap<Point, Node>();
//        HashMap<Point, Node> closedList = new HashMap<Point, Node>();
//        //closed list uses hashset
//        //open list uses hashMap, and priority queue
//
//        Node firstNode = new Node(null, start, 0, calculateH(start, end), calculateH(start, end));
//        openListQ.add(firstNode); //start node
//
//        Node cur = null;
//
//
//        while(!openListQ.isEmpty()){
//            //openListQ.forEach(n->n.getLoc().printLoc());
//            cur = openListQ.remove();
//
//            if(withinReach.test(cur.getLoc(), end)){
//                while(cur.getPrev() != null){
//                    path.add(cur.getLoc());
//                    cur = cur.getPrev();
//                }
//                Collections.reverse(path);
//                return path;
//            }
//
//            List<Point> neighbors = potentialNeighbors  //find neighbors
//                    .apply(cur.getLoc())
//                    .filter(canPassThrough)
//                    .filter(point -> !point.equals(start) && !point.equals(end))
//                    .toList();
//
//            for(Point p: neighbors){  //check neighbors of current
//                int new_g = cur.getG() + 1;
//                Node newNode = new Node(cur, p, new_g, calculateH(p, end), calculateH(p, end) + new_g);
//                if(!closedList.containsKey(p)){ //if neighbor not already searched, then:
//                     //any neighbor will have current node g value + 1
//
//                    if(openListM.containsKey(p)) { //check if in open list
////                        int cur_g = 0;
////                        for (Node n : openListQ) {
////                            if (n.getLoc().equals(p)) {
////                                cur_g = n.getG();  //get current g value
////                            }
////                        }
//                        int cur_g = openListM.get(p).getG();
//                        if (new_g < cur_g) {  //replace node with better g value
//                            openListQ.remove(newNode);
//                            openListQ.add(newNode);
//                            openListM.replace(p, newNode);
//
//                        }
//                    } else { //add node if not already in open list
//                        //Node neighbor = new Node(cur, p, cur.getG() + 1, calculateH(p, end), calculateH(p, end) + cur.getG() + 1);
//                        openListQ.add(newNode);
//                        openListM.put(p, newNode);
//                    }
//                    // //add node to closed list once already searched
//                }
//
//            }
//            closedList.put(cur.getLoc(), cur);
//        }
//        return path;
//    }
//
//    private double calculateH(Point pos, Point destPos){
//        return Point.distance(pos, destPos);
//    }
//}
//
//
////        for(Node n: openListQ){
////            System.out.print(openListQ.element().getLoc().getX());
////            System.out.print(" , ");
////            System.out.println(openListQ.element().getLoc().getY());
////        }

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();

        Queue<Node> openListQ = new PriorityQueue<Node>(Comparator.comparing(Node::getF));
        HashSet<Point> openListS = new HashSet<Point>();
        HashSet<Point> closedList = new HashSet<Point>();

        Node firstNode = new Node(null, start, 0, calculateH(start, end), calculateH(start, end));
        openListQ.add(firstNode); //start node

        Node cur = null;

        while(!openListQ.isEmpty()){
            cur = openListQ.remove();

            if(withinReach.test(cur.getLoc(), end)){
                while(cur.getPrev() != null){
                    path.add(cur.getLoc());
                    cur = cur.getPrev();
                }
                Collections.reverse(path);
                return path;
            }

            List<Point> neighbors = potentialNeighbors  //find neighbors
                    .apply(cur.getLoc())
                    .filter(canPassThrough)
                    .filter(point -> !point.equals(start) && !point.equals(end))
                    .toList();

            for(Point p: neighbors){  //check neighbors of current
                int new_g = cur.getG() + 1;  //any neighbor will have current node g value + 1
                Node newNode = new Node(cur, p, new_g, calculateH(p, end), calculateH(p, end) + new_g);
                if(!closedList.contains(p)){ //if neighbor not already searched, then:
                    if(openListS.contains(p)) { //check if in open list
                        if (new_g < cur.getG()) {  //replace node with better g value
                            openListQ.remove(newNode);
                            openListQ.add(newNode);
//                            openListS.remove(p);
//                            openListS.add(newNode.getLoc());
                        }
                    } else { //add node if not already in open list
                        openListQ.add(newNode);
                        openListS.add(p);
                    }
                }
            }
            closedList.add(cur.getLoc());  //add node to closed list once already searched
        }
        return path;
    }

    private double calculateH(Point pos, Point destPos){
        return Point.distance(pos, destPos);
    }
}