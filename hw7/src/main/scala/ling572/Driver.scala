package ling572

import svm.SVMModel
import java.io.{PrintWriter, File}
import scala.collection._
import scala.collection.JavaConverters._
import annotation.tailrec
import util.{VectorInstance, SVMLightReader}

object Driver extends App {

  implicit class NullCoalescent[A](a: A){
    def ??(b: => A) = if(a!=null) a else b
  }

  var testData:File = null
  var outputFile:File = null
  var modelFile:File = null

  ////////////// argument parsing ///////////////////

  def exit(errorText:String) {
    System.out.println(errorText)
    System.exit(1)
  }

  if (args.length != 3)
   exit("Error: usage Driver test_data model_file sys_output")

  try {
    this.testData = new File(args(0))
  } catch {
    case e:Exception => exit("Error: invalid test_data file")
  }

  try {
    this.modelFile = new File(args(1))
  } catch {
    case e:Exception => exit("Error: invalid model_file file")
  }

  try {
    this.outputFile = new File(args(2))
  } catch {
    case e:Exception => exit("Error: invalid sys_output file")
  }


  ////////////// setup ///////////////////
  val sysOut = new PrintWriter(outputFile)
  
  val svfile = new SupportVectorFile()
  svfile.read(modelFile)
  val gamma = svfile.getGamma ?? 1.0
  val degree = svfile.getDegree ?? 1.0
  val coef0 = svfile.getCoef0 ?? 0.0
  val rho = svfile.getRho ?? 0.0
  val model = new SVMModel(gamma = gamma, degree = degree, coef0 = coef0, rho = rho, kernelType = svfile.getKernelType)
  model.setSupportVectors(svfile.getVectorInstances)

  val testInstances = SVMLightReader.indexInstances(testData)
  



  ///////////// search /////////////////
  //sysOut.println("instance count: " + svfile.getVectorInstances.size)
  //sysOut.println("gamma: " + gamma + "  degree: " + degree + "  coef0: " + coef0 + "  rho: " + rho + "  kernel: " + svfile.getKernelType)


  //sysOut.println()


  var count = 0
  var correct = 0
  testInstances.asScala.foreach { case (instance: VectorInstance) =>
     val (classLabel, score) = model.classifyInstance(instance)
     count += 1
     if (classLabel.toString.equals(instance.getLabel)) correct += 1
     sysOut.println(instance.getLabel + " " + classLabel + " " + score.toString)
  }

  sysOut.println("Accuracy: " + correct.toDouble / count.toDouble)
  sysOut.close()
}

