package aggregation;

import spaces.Spaces;
import sweep.SimStateSweep;

// inherited is checked in creation not constructor
// add constructor long class to fix error 

public class Environment extends SimStateSweep {
	
	public int n = 2000; // number of agents
	public boolean oneAgentPerCell = true; //is there only one agent per space
	public double active = 1.0; // probability that each agent is active in each time step
	public double p = 1.0; // probability of random movement
	public double aggregate = 0.0; //should agents move in toward each other (when aggregate = 0 they do not move toward each other)
	public int searchRadius = 2; //how many squares should agents search for neighbors (AKA it's "view")

	// source --> generate getters and setters
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public boolean isOneAgentPerCell() {
		return oneAgentPerCell;
	}

	public void setOneAgentPerCell(boolean oneAgentPerCell) {
		this.oneAgentPerCell = oneAgentPerCell;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getAggregate() {
		return aggregate;
	}

	public void setAggregate(double aggregate) {
		this.aggregate = aggregate;
	}

	public int getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}
	
	public void makeAgents() {
        // TODO see pseudocode
		if(oneAgentPerCell && n > (gridWidth * gridHeight)) {
			System.out.println("too many agents: " + n);
			return;
		}
		for(int i = 0; i < n; i++) {
			int x = random.nextInt(gridWidth);
			int y = random.nextInt(gridHeight);
			if(oneAgentPerCell) {
				while(this.sparseSpace.getObjectsAtLocation(x, y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				}
			}
			// placing 3 in will give back 0, 1, 2 and -1 will shift it so that -1, 0, and 1 are returned
			int dirx = random.nextInt(3)-1;
			int diry = random.nextInt(3)-1;
			Agent a = new Agent(x, y, dirx, diry);
			//schedule thea gent
			schedule.scheduleRepeating(a);
			// put the agent into space
			this.sparseSpace.setObjectLocation(a, x, y);
			
		}
	}
	
	//just starting coding from here down ignores all previous code so we use super
	
	public void start() {
		super.start();
		this.make2DSpace(Spaces.SPARSE, this.gridWidth, this.gridHeight);
        makeAgents();  
	}
	
}