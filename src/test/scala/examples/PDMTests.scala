package examples



import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester, TesterOptionsManager, SteppedHWIOTester}    
import chisel3._   
import chisel3.util._
import org.scalatest.{Matchers, FlatSpec}



/*
import grapher.Grapher
import javax.swing.JFrame
import java.awt.Color
*/


class PDMTests(c:PDM) extends PeekPokeTester(c)             //random waveform
{
   //var set = Array(1,2,3,4,5,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-4,-3,-2,-1,0,1,2,3,4,5)

   var set =Array(1,2,3,4,5,6,7,8,9,8,7,6,5,4,3,2,1,0,1,2,3,4,5,6,7,8,9,8,7,6,5,4,3,2,1)    //,-1,-2,-3,-4,-5,-6,-7,-8,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,8)

  //var set = Array(2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2)



    var k = 0


	for(i <- 0 until 25)
	{
        k = k + 1
        poke(c.io.inp,set(k))
       for(i<- 0 until 50)       
        { 
		//poke(c.io.inp,set(k))

        if(set(k) >= peek(c.io.qer)){
        	 
        	 expect(c.io.out,true)

        }
        else{
        	
        	 expect(c.io.out,false)
        }


        step(1)

   }

    step(1)


}


}




class PDM3Tests(c:PDM) extends PeekPokeTester(c)             //random waveform
{
   //var set = Array(1,2,3,4,5,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-4,-3,-2,-1,0,1,2,3,4,5)

   var set =Array(1,2,3,4,5,6,7,8,9)    //,-1,-2,-3,-4,-5,-6,-7,-8,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,8)

  //var set = Array(2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2)



    var k = 0


	for(i <- 0 until 8)
	{
        k = k + 1
        poke(c.io.inp,set(k))
       for(i<- 0 until 50)       
        { 
		//poke(c.io.inp,set(k))

        if(set(k) >= peek(c.io.qer)){
        	 
        	 expect(c.io.out,true)

        }
        else{
        	
        	 expect(c.io.out,false)
        }


        step(1)

   }

    step(1)


}


}













class PDM2Tests(c:PDM) extends PeekPokeTester(c)             //random waveform
{
   



//object SineWave extends App {
  def sineWave(waveFrequency: Double, magnitude: Int, sampleFrequency: Double, samples: Int): IndexedSeq[Int] = {
    val wavePeriod = 1 / waveFrequency
    val samplePeriod = 1 / sampleFrequency
    val dTheta = (samplePeriod / wavePeriod) * 2 * math.Pi
    
    (0 until samples).map(n => (math.sin(dTheta * n).asInstanceOf[Int] * magnitude) ) //+  magnitude/2)
  }
//  }
  

  /*
  val grapher = new Grapher
  val frame = new JFrame("SquareWave")
  frame.getContentPane().add(grapher)
  frame.pack
  frame.setVisible(true)
  frame.setSize(1000, 1000)
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  

  */


  val waveFrequency = 1
  val magnitude = 10
  val sampleFrequency = 1000
  val samples = 2000
  
  
  val sineWaveData = sineWave(2, magnitude, sampleFrequency, samples)

  //val sineWaveData = sineWave1.asInstanceOf[Int]
  
    

    var k = 0
	for(i <- 0 until (sineWaveData.length-2))
	{
        k = k + 1
        poke(c.io.inp,sineWaveData(k))
       for(i<- 0 until 10)       
        { 


        if(sineWaveData(k) >= peek(c.io.qer)){
        	 
        	 expect(c.io.out,true)

        }
        else{
        	
        	 expect(c.io.out,false)
        }


        step(1)

   }

    step(1)


}


}

















/*

class PDMTests(c:PDM) extends PeekPokeTester(c)           //test
{
    poke(c.io.qer,0)
    poke(c.io.inp,1)
    step(1)

    expect(c.io.out,1)







}


*/



/*


class PDMTests(c:PDM) extends PeekPokeTester(c)
{



   var k: Int = -1
	poke(c.io.qer,0)
	step(1)
	for(i <-0 to 100)
        step(1)
        k = k+1

		val ins=k/100
		poke(c.io.inp,ins)
      

        if(ins>=peek(c.io.qer)){
        	 expect(c.io.out,1)

        }
        else{
        	 expect(c.io.out,-1)
        }


}


*/


/*

class PDMTester extends ChiselFlatSpec {
  behavior of "PDM"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new PDM())(c => new PDMTests(c)) should be (true)
    }
  }
}


*/



class PDMfirrtlTester extends ChiselFlatSpec{
   behavior of "PDM"

 it should "run firrtl via command line arguments" in {
    // val args = Array.empty[String]                                                     //classfor FIRTEL     
    val args = Array("--backend-name", "firrtl", "--fint-write-vcd")
    iotesters.Driver.execute(args, () => new PDM()) { c =>
      new PDMTests(c)
    } should be (true)
  }
}




class PDM2firrtlTester extends ChiselFlatSpec{
   behavior of "PDM"

 it should "run firrtl via command line arguments" in {
    // val args = Array.empty[String]     
     val args = Array("--backend-name", "firrtl", "--fint-write-vcd")                          
    iotesters.Driver.execute(args, () => new PDM()) { c =>
      new PDM2Tests(c)
    } should be (true)
  }
}
