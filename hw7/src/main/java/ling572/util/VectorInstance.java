package ling572.util;

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.SparseDoubleMatrix1D;

import java.util.Map;

public class VectorInstance implements Instance<Double> {

    private DoubleMatrix1D vector = new SparseDoubleMatrix1D(100);
    private String label;
    private String name;
    private double weight;

    /**
     * Assumes libSVM format (i.e. integer-valued) features
     * @param feature
     * @param value
     */
    public void addFeature(String feature, Double value) {
        int feat_idx = Integer.parseInt(feature);
        addFeature(feat_idx, value);
    }

    public void addFeature(int feat_idx, Double value) {
        vector.set(feat_idx, value);
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return vector.cardinality();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setWeight(double weight) {
    	this.weight = weight;
    }
    
    public double getWeight() {
    	return this.weight;
    }

    public boolean containsFeature(String feature) {
        int feat_idx = Integer.parseInt(feature);
        return containsFeature(feat_idx);
    }

    public boolean containsFeature(int feat_idx)  {
        return (vector.get(feat_idx) != 0);
    }

    public Double getFeatureValue(String feature) {
        int feat_idx = Integer.parseInt(feature);
        return getFeatureValue(feat_idx);
    }

    public Double getFeatureValue(int feat_idx) {
        return Double.valueOf(vector.get(feat_idx));
    }

    public Double getFeatureValueOrDefault(String feature, Double val) {
        int feat_idx = Integer.parseInt(feature);
        return getFeatureValueOrDefault(feat_idx, val);
    }

    public Double getFeatureValueOrDefault(int feat_idx, Double val) {
        if (feat_idx > vector.size())
            return val;
        else
            return vector.get(feat_idx);
    }

    public Map<String, Double> getFeatures() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeFeature(String feature) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public DoubleMatrix1D getVector() {
        return vector;
    }
}
