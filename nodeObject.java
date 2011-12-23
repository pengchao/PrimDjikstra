// William Gimson
// Experimental Assigment 2
// CSc 4520
// Dr. Prasad
package experimentalassignment2;

public class nodeObject {
    
    // instance variables
    int nodeCount, near, nearWeight, selfWeight;
    nodeObject parent;
    
    
    // nodeObject constructor - 'nodeCount' represents the node constucted, and 
    // is the only parameter passed during construction - 'near' represents the 
    // next nearest node; this changes as new nodes are added to the LinkedList 
    // inTree and simultaneoously removed from the pool of nodes which can be
    // 'near' - 'nearWeight' represents the weight between the nodeObject which
    // is inTree, and that node object's 'nearest' node; this weight must be the 
    // least of all potential weights, because that's what 'nearest' is defined 
    //  to mean - 'selfWeight' represents the weight taken on by the graph when
    // a node is added to inTree; this does not change, and the sum total of all 
    // 'selfWeights' in the full LinkedList inTree represents the total weight
    // of the final product, which is an MST - 'parent' represents the parent 
    // node of a node in inTree
    public nodeObject(int i) {
        this.nodeCount = i;
        // initialized to the first node added to inTree - this is arbitrary
        this.near = 0;
        // parent initialized to null - will remain null for first node inTree
        this.parent = null;
        // initialized to MAX_POSSIBLE_INT - again, arbitrary but also for error
        // detection
        this.nearWeight = primDjikstra.MAX_POSSIBLE_INT;
        this.selfWeight = primDjikstra.MAX_POSSIBLE_INT;
    }
 
    // getters
    public int getNodeCount() {
        return this.nodeCount;
    }
    
    public int getNearWeight() {
        return this.nearWeight;
    }
    
    public int getNear() {
        return this.near;
    }
    
    public int getSelfWeight() {
        return this.selfWeight;
    }
    
    public int getParent() {
        return this.parent.getNodeCount();
    }
    
    // setters
    public void setNearWeight(int w) {
        this.nearWeight = w;
    }
    
    public void setSelfWeight(int w) {
        this.selfWeight = w;
    }
    
    public void setNear(int n) {
        this.near = n;
    }
    
    public void setParent(nodeObject n) {
        this.parent = n;
    }
}

