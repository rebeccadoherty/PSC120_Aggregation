package aggregation;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

//to correct indentation: source --> correct indentation

public class Agent implements Steppable {
	
	int x; // x coordinate in space
	int y; // y coordinate in space
	int dirx; // x direction of change
    int diry; // y direction of change
    
    //source --> generate constructor using fields

	public Agent(int x, int y, int dirx, int diry) {
          super();
          this.x = x;
          this.y = y;
          this.dirx = dirx;
          this.diry = diry;
    }
	
    public void aggregate(Environment state) {
    	if(state.random.nextBoolean(state.active)) {
    		Bag neighbors = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, true);
    		dirx = decidex(state, neighbors);
    		diry = decidey(state, neighbors);
    		placeAgent(state);
    	}
    }
    
    public int decidex(Environment state, Bag neighbors) {
    	int posx = 0, negx = 0;
    	for(int i = 0; i < neighbors.numObjs; i++) {
    		Agent a = (Agent)neighbors.objs[i];
    		if(a.x > this.x) {
    			posx++;
    		}
    		else if (a.x < this.x) {
    			negx++;
    		}
    	}
    	if(posx > negx) {
    		return 1;
    	}
    	else if(negx > posx) {
    		return -1;
    	}
    	else {
    		return state.random.nextInt(3)-1;
    	}
    }
    
    public int decidey(Environment state, Bag neighbors) {
    	int posy = 0, negy = 0;
    	for(int i = 0; i < neighbors.numObjs; i++) {
    		Agent a = (Agent)neighbors.objs[i];
    		if(a.y > this.y) {
    			posy++;
    		}
    		else if (a.y < this.y) {
    			negy++;
    		}
    	}
    	if(posy > negy) {
    		return 1;
    	}
    	else if(negy > posy) {
    		return -1;
    	}
    	else {
    		return state.random.nextInt(3)-1;
    	}
    }
	
	public void move(Environment state) {
        if(state.random.nextBoolean(state.active)) {
            if(state.random.nextBoolean(state.p)) {
            	dirx = state.random.nextInt(3)-1;
            	diry = state.random.nextInt(3)-1;
            }
            placeAgent(state);
        }
   }

   //we will need to add more to this, but for now, let's just set up the skeleton
	public void placeAgent(Environment state) {
		if(state.oneAgentPerCell) {
			int tempx = state.sparseSpace.stx(x + dirx);
			int tempy = state.sparseSpace.sty(y + diry);
			if(state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);
			}
		}
		else {
			x = state.sparseSpace.stx(x + dirx);
			y = state.sparseSpace.sty(y + diry);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}

   //we will need to add more to this, but for now, let's just set up the skeleton
   @Override
   public void step(SimState state) {
       Environment e = (Environment)state; 
	   if(e.random.nextBoolean(e.aggregate)){
		   aggregate(e);  
	   }
	   else {
		   move(e);
	   }
		   
   }

}
