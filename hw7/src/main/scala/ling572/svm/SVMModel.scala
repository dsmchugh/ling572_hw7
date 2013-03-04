package ling572.svm

import ling572.util.VectorInstance
import cern.colt.matrix.tdouble.algo.DoubleStatistic
import cern.colt.matrix.tdouble.{DoubleFactory1D, DoubleMatrix1D}
import scala.collection.JavaConverters._
import ling572.KernelType

class SVMModel(val gamma:Double = 1.0, val coef0:Double = 0.0, val degree:Double = 1.0, val rho:Double = 0.0,
               var kernelType:KernelType = KernelType.linear) {

  type KernelMethod = (DoubleMatrix1D, DoubleMatrix1D) => Double

  //////////////////////////////
  // KERNELS

  var supportVectors: List[VectorInstance] = null

  val linear:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => u.zDotProduct(v)

  val polynomial:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    math.pow(gamma * u.zDotProduct(v) + coef0, degree)
  }

  val rbf:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    // need to extend the shorter vector with zeroes; euclidean function requires same size
    val sdiff:Int = u.size.toInt - v.size.toInt
    val zeros = DoubleFactory1D.dense.make(math.abs(sdiff))
    val u1 = if (sdiff < 0) DoubleFactory1D.dense.append(u,zeros) else u
    val v1 = if (sdiff > 0) DoubleFactory1D.dense.append(v,zeros) else v
    val dist = DoubleStatistic.EUCLID.apply(u1, v1)
    math.exp(-gamma * dist * dist)
  }

  val sigmoid:KernelMethod = (u:DoubleMatrix1D, v:DoubleMatrix1D) => {
    math.tanh(gamma * u.zDotProduct(v) + coef0)
  }


  ///////////////////////////////
  // SUPPORT

  def setSupportVectors(vectors:List[VectorInstance]) {
    supportVectors = vectors
  }

  def setSupportVectors(vectors:java.util.List[VectorInstance]) {
    supportVectors = vectors.asScala.toList
  }


  /////////////////////////////////
  // SCORING

  def scoreInstance(u:VectorInstance, kernel:KernelMethod):Double = {
    val uv = u.getVector
    supportVectors.par.map(v => v.getWeight * kernel(uv,v.getVector)).sum - rho
  }

  def classifyInstance(instance:VectorInstance):(Int,Double) = {
    val kernel =  kernelType match {
      case KernelType.linear => linear
      case KernelType.polynomial => polynomial
      case KernelType.rbf => rbf
      case KernelType.sigmoid => sigmoid
    }
    val score = scoreInstance(instance, kernel)
    ((if (score >= 0) 0 else 1), score)
  }


}
