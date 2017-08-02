package soot.spl.ifds;

import soot.Unit;
import soot.tagkit.Host;
import heros.EdgeFunction;
import heros.edgefunc.AllTop;
import heros.edgefunc.EdgeIdentity;

public class SPLFeatureFunction implements EdgeFunction<IConstraint> {
	
	protected final IConstraint features;
	
	public IConstraint getFeatures() {
		return features;
	}

	public SPLFeatureFunction(IConstraint features){
		this.features = features;
	} 
	
	public IConstraint computeTarget(IConstraint source) {
		IConstraint conjunction = source.and(features);
		return conjunction;
		//return conjunction.considerFeatureModel(fmContext);
	}

	public EdgeFunction<IConstraint> composeWith(EdgeFunction<IConstraint> secondFunction) {
		if(secondFunction instanceof EdgeIdentity || secondFunction instanceof AllTop) {
			System.out.println("COMPOSING >>>>> [" + features + "] AND [" + secondFunction + "] ========> " + this);
			return this;
		}
		
		SPLFeatureFunction other = (SPLFeatureFunction)secondFunction;
		SPLFeatureFunction res = new SPLFeatureFunction(features.and(other.features));
		System.out.println("COMPOSING >>>>> [" + features + "] AND [" + secondFunction + "] ========> " + res);
		
		return new SPLFeatureFunction(features.and(other.features));
	}

	public EdgeFunction<IConstraint> joinWith(EdgeFunction<IConstraint> otherFunction) {
		//here we implement union/"or" semantics
		if(otherFunction instanceof AllTop) {
			System.out.println("JOINING >>>>> [" + features + "] OR [" + otherFunction + "] ========> " + this);
			return this;
		}
		
		if(otherFunction instanceof EdgeIdentity) {
			System.out.println("JOINING >>>>> [" + features + "] OR [" + otherFunction + "] ========> " + otherFunction);
			return otherFunction;
		}

		SPLFeatureFunction other = (SPLFeatureFunction)otherFunction;
		SPLFeatureFunction res = new SPLFeatureFunction(features.or(other.features));
		System.out.println("JOINING >>>>> [" + features + "] OR [" + otherFunction + "] ========> " + res);
		
		return res;
	}
	
	public boolean equalTo(EdgeFunction<IConstraint> other) {
		if(other instanceof SPLFeatureFunction) {
			SPLFeatureFunction function = (SPLFeatureFunction) other;
			return function.features.equals(features);
		}
		return false;
	}

	public String toString() {
		return features.toString();
	}


}
