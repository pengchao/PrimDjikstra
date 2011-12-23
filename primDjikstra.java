// William Gimson
// Experimental Assignment 2
// CSc 4520
// Dr. Prasad
package experimentalassignment2;

import java.util.*;

public class primDjikstra {
    
    // this int constant will represent nodes that have already been added 
    // to LinkedList inTree in the two dimensional array testMatrix as well
    // as node links which are tautological (node i to node i)
    public static final int MAX_POSSIBLE_INT = 2147483647;
    
    public static void main(String[] args) {
    
        // variable declarations
        int initialNearest, minWeight, totalWeight;
        long startTime, endTime;
        
        // create LinkedList to hold nodes which are in the tree
        LinkedList inTree = new LinkedList<nodeObject>();
        
        // generate two dimensional graph with randomly generated weights from
        // 1 to 101 - I did this rather than, say, from 0 - 100, because at
        // large input levels, there will always be another node to which a 
        // node inTree connects with a weight of zero, resulting in the 
        // paradoxical situation where larger and larger graph matrices produce
        // MST with total weights closer and closer to 0! - which just seems 
        // wrong somehow - still, at higher inputs the Math.random() generator
        // will always produce enough integers of the least possible magnitude
        // such that *every* new nodeObject added to inTree will have a 
        // 'selfWeight' of this least possible amount attached to it, and
        // totalWeight will be approximately ((testMatrix.length * minimum) - 1)
        // - user may wish to constrain the range of weights testMatrix can 
        // contain for this reason
        int[][] testMatrix = new int[10][10];
        for (int i = 0; i < testMatrix.length; i++) {
            for (int j = 0; j < testMatrix.length; j++) {
                if (i == j) {
                    testMatrix[i][j] = MAX_POSSIBLE_INT;
                } else {
                    testMatrix[i][j] = ((int)(Math.random() * 100) + 1);
                }
            }
        }
        
        // put the first node of the two dimensional matrix into inTree 
        // arbitrarily
        nodeObject firstNodeInTree = new nodeObject(0);
        inTree.addLast(firstNodeInTree);
        
        // the first node's self weight is always 0 - it's parent is always null
        // (this is tautological here, because new nodeObjects are initialized 
        // to null parents by default anyway, but it helps to be explicit I
        // think)
        firstNodeInTree.setSelfWeight(0);
        firstNodeInTree.setParent(null);
        
        // set firstNodeInTree's near and weight instance variables - initialize
        // them arbitrarily to the nodeObject 0 and the weight at 
        // testMatrix[0][0], then iterate through row 0 of testMatrix and 
        // replace these with the actual least weight and corresponding node
        minWeight = testMatrix[firstNodeInTree.getNodeCount()][0];
        initialNearest = 0;
        firstNodeInTree.setNear(initialNearest);
        firstNodeInTree.setNearWeight(minWeight);
        for (int i = 1; i < testMatrix.length; i++) {
            if (testMatrix[firstNodeInTree.getNodeCount()][i] <
                    minWeight) {
                minWeight = testMatrix[firstNodeInTree.getNodeCount()][i];
                firstNodeInTree.setNearWeight
                        (minWeight);
                firstNodeInTree.setNear(i);
            }
        }
        
        // set all matrix nodes pointing to firstNodeInTree to have weight
        // MAX_POSSIBLE_INT so that a node pointing to another node already
        // inTree will never have a minimum weight in a row
        for (int i = 0; i < testMatrix.length; i++) {
            testMatrix[i][firstNodeInTree.getNodeCount()] = MAX_POSSIBLE_INT;
        }
        
        // start measuring time of algorithm addNode here
        startTime = System.currentTimeMillis();
        do {
            
            // this function adds new nodes to inTree and also updates the 
            // 'near' and 'nearWeight' instance variables of nodeObjects in
            // inTree as new nodeObjects are added
            addNode(inTree, testMatrix);
            
            // once inTree contains as many nodeObjects as testMatrix is long,
            // we are finished
        } while (inTree.size() < testMatrix.length);
        
        // get endTime
        endTime = System.currentTimeMillis();

        // print total MST weight (add up self weights for all node in inTree)
        totalWeight = 0;
        for (int i = 0; i < inTree.size(); i++) {
            nodeObject summedNode = (nodeObject)inTree.get(i);
            totalWeight += summedNode.getSelfWeight();
        }

        System.out.println("\n            MST WEIGHT ANALYSIS                ");
        System.out.println("-------------------------------------------------");
        System.out.println("The total weight of the MST is: " + totalWeight);

        // print parent-child relationship of MST
        System.out.println("\n             TRAVERSAL ANALYSIS                ");
        System.out.println("-------------------------------------------------");
        for (int i =  1; i < inTree.size(); i++) {
            nodeObject findParent = (nodeObject)inTree.get(i);
            System.out.println("Node " + findParent.getNodeCount()
                    + "'s parent node is: " + findParent.getParent());
        }
        
        // print total run time
        System.out.println("\n           TIME COMPLEXITY ANALYSIS            ");
        System.out.println("-------------------------------------------------");
        System.out.println("Total run time to find an MST composed of " + 
                testMatrix.length + " inputs was: " + (endTime - startTime)
                + " milliseconds.");
    }
    
    // the first time this function is called, LinkedList inTree has already had
    // one nodeObject arbitrarily added to it, so now we can traverse inTree
    // and add the node represented by the instance variable 'near' of the sole
    // nodeObject, for which the smallest value of the instance variable weight
    // is attached
    private static void addNode(LinkedList inTree, 
            int[][] twoDimensionalMatrix) {

        // variable declarations
        nodeObject nextNodeInTree, hasMin;
        int minWeight, nearest;
        
        // set hasMin to the first element of inTree - this nodeObject 
        // represents the parent of the next node to go into inTree
        hasMin = (nodeObject)inTree.get(0);
        
        // for inTree of size 1, no iteration is needed - the near instance
        // variable of the only node represents the next node to be added - 
        // nextNodeInTree's 'parent' and 'selfWeight' instance variables will
        // be nodeObject's hasMin and hasMin's 'nearWeight' instance variables, 
        // respectively
        if (inTree.size() == 1) {
            nextNodeInTree = new nodeObject(hasMin.getNear());
            inTree.addLast(nextNodeInTree);
            nextNodeInTree.setSelfWeight(hasMin.getNearWeight());
            nextNodeInTree.setParent(hasMin);
            
            // initialize near and weight instance variables for nextNodeInTree
            // - changing the weights in twoDimensionalMatrix for all entries
            // that point to nodeObject nextNodeInTree can wait until after 
            // this step, since nextNodeInTree's weight to itself will already
            // be MAX_POSSIBLE_INT, and therefore can't possibly be the least
            // weight - intitializations are made arbitrarily to nodeObject 0
            // and it's nearWeight
            minWeight = twoDimensionalMatrix[nextNodeInTree.getNodeCount()][0];
            nearest = 0;
            nextNodeInTree.setNear(nearest);
            nextNodeInTree.setNearWeight(minWeight);
            for (int i = 1; i < twoDimensionalMatrix.length; i++) {
                if (twoDimensionalMatrix[nextNodeInTree.getNodeCount()][i] <
                        minWeight) {
                    minWeight = 
                            twoDimensionalMatrix
                            [nextNodeInTree.getNodeCount()][i];
                    nextNodeInTree.setNearWeight
                            (minWeight);
                    nextNodeInTree.setNear(i);
                }
            }
  
            // set all matrix nodes pointing to firstNodeRemoved to 
            // MAX_POSSIBLE_INT so that a node pointing to it will never be
            // a minimum
            for (int i = 0; i < twoDimensionalMatrix.length; i++) {
                twoDimensionalMatrix[i][nextNodeInTree.getNodeCount()]
                        = MAX_POSSIBLE_INT;
            }
            
            // NOW we update every inTree nodeObject's 'near' and 'nearWeight'
            // instance variables, because the twoDimensionalMatrix entries
            // have been altered to reflect the latest node addition to inTree
            for (int i = 0; i < inTree.size(); i++) {
                
                // call whichever node (matrix row) we happen to be checking
                // 'checkNode'
                nodeObject checkNode = (nodeObject)inTree.get(i);
                
                // if checkNode's 'near' has been added, and the corresponding 
                // 'nearWeight' has been changed to MAX_POSSIBLE_INT, then 
                // we must update these instance variables
                if (twoDimensionalMatrix[checkNode.getNodeCount()]
                        [checkNode.getNear()] == MAX_POSSIBLE_INT) {
                    
                    // initialize minWeight and nearest to the matrix entry at
                    // row nodeCount column 0
                    minWeight = twoDimensionalMatrix[checkNode.getNodeCount()]
                            [0];
                    nearest = 0;
                    
                    // check every matrix entry for row nodeCount of checkNode
                    // - replace checkNode's 'nearWeight' and corresponding 
                    // 'near' with any that are less
                    for (int j = 1; j < twoDimensionalMatrix.length; j++) {
                        if (twoDimensionalMatrix[checkNode.getNodeCount()][j]
                                < minWeight) {
                            minWeight = twoDimensionalMatrix
                                    [checkNode.getNodeCount()][j];
                            checkNode.setNearWeight(minWeight);
                            checkNode.setNear(j);
                        }
                    }
                }
            }

            // when inTree has more than one nodeObject, we must iterate through
            // it to find the node from which the next nodeObject should 
            // descend, i.e. the node with the least 'nearWeight'
        } else {

            //int nodeCounter = 0;
            //nodeObject minNode = (nodeObject)inTree.get(0);
            minWeight = hasMin.getNearWeight();
            
            // iterate through LinkedList inTree, and find the nodeObject with
            // the least 'nearWeight' - the associated 'near' node will become
            // nextNodeInTree, and hasMin's 'nearWeight' will become 
            // nextNodeInTree's 'selfWeight'
            for (int i = 1; i < inTree.size();  i++) {
                
                // checkNode represents the nodeObject we are currently checking
                nodeObject checkNode = (nodeObject)inTree.get(i);
                
                // if checkNode's 'nearWeight' is less than the hasMin's 
                // 'nearWeight', then checkNode is now hasMin
                if (checkNode.getNearWeight() < minWeight) {
                    minWeight = checkNode.getNearWeight();
                    hasMin = checkNode;
                }
            }
            
            // create new node object to go in tree from hasMin's nearest node
            // - nextNodeInTree's 'selfWeight' and 'parent' instance variables
            // will be hasMin's 'getNearWeight' instance variable and hasMin
            // itself, repectively
            nextNodeInTree = new nodeObject(hasMin.getNear());
            inTree.addLast(nextNodeInTree);
            nextNodeInTree.setSelfWeight(hasMin.getNearWeight());
            nextNodeInTree.setParent(hasMin);
            
            // initialize near and weight instance variables for nextNodeInTree
            // - changing the weights in twoDimensionalMatrix for all entries
            // that point to nodeObject nextNodeInTree can wait until after 
            // this step, since nextNodeInTree's weight to itself will already
            // be MAX_POSSIBLE_INT, and therefore can't possibly be the least
            // weight - intitializations are made arbitrarily to nodeObject 0
            // and it's nearWeight
            minWeight = twoDimensionalMatrix[nextNodeInTree.getNodeCount()][0];
            nearest = 0;
            nextNodeInTree.setNear(nearest);
            nextNodeInTree.setNearWeight(minWeight);
            for (int i = 1; i < twoDimensionalMatrix.length; i++) {
                if (twoDimensionalMatrix[nextNodeInTree.getNodeCount()][i] <
                        minWeight) {
                    minWeight = 
                            twoDimensionalMatrix
                            [nextNodeInTree.getNodeCount()][i];
                    nextNodeInTree.setNearWeight
                            (minWeight);
                    nextNodeInTree.setNear(i);
                }
            }
            
            // set all matrix nodes pointing to nextNodeInTree to
            // MAX_POSSIBLE_INT so that a node pointing to it will never be 
            // a minimum
            for (int i = 0; i < twoDimensionalMatrix.length; i++) {
                twoDimensionalMatrix[i][nextNodeInTree.getNodeCount()]
                        = MAX_POSSIBLE_INT;
            }
            
            // NOW we update every inTree nodeObject's 'near' and 'nearWeight'
            // instance variables, because the twoDimensionalMatrix entries
            // have been altered to reflect the latest node addition to inTree
            for (int i = 0; i < inTree.size(); i++) {
                
                // call whichever node (matrix row) we happen to be checking
                // 'checkNode'
                nodeObject checkNode = (nodeObject)inTree.get(i);
                
                // if checkNode's 'near' has been added, and the corresponding 
                // 'nearWeight' has been changed to MAX_POSSIBLE_INT, then 
                // we must update these instance variables
                if (twoDimensionalMatrix[checkNode.getNodeCount()]
                        [checkNode.getNear()] == MAX_POSSIBLE_INT) {
                    
                    // initialize minWeight and nearest to the matrix entry at
                    // row nodeCount column 0
                    minWeight = twoDimensionalMatrix[checkNode.getNodeCount()]
                            [0];
                    nearest = 0;
                    
                    // check every matrix entry for row nodeCount of checkNode
                    // - replace checkNode's 'nearWeight' and corresponding 
                    // 'near' with any that are less
                    for (int j = 1; j < twoDimensionalMatrix.length; j++) {
                        if (twoDimensionalMatrix[checkNode.getNodeCount()][j]
                                < minWeight) {
                            minWeight = twoDimensionalMatrix
                                    [checkNode.getNodeCount()][j];
                            checkNode.setNearWeight(minWeight);
                            checkNode.setNear(j);
                        }
                    }
                }
            }
        }
 
        return;
    }
}
