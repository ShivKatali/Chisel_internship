package examples



import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}





import java.io.File          
import chisel3._                                      //add these to run testerclass for verilator treadle and firrtl
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{FlatSpec, Matchers}



import treadle.TreadleTester
import org.scalatest.{Matchers, FlatSpec}



class Test_pwmTests(c: Test_pwm) extends PeekPokeTester(c) {

for(i <- 0 until 10){

val riseval =      rnd.nextInt((1 << (c.n-1))-2)+ 1
val fallval =      rnd.nextInt((1 << (c.n-1))-2)+1

//val fallvalcor =fallval -1

poke(c.io.rise,riseval)
poke(c.io.fall,fallval)
poke(c.io.reset,false)
println(riseval +","+fallval)



for(i <- 0 until 2){
  for(j <- 0 until riseval){
      step(1)
      expect(c.io.out,1)
    
  }
  for(k<- 0 until fallval){                        //to changed to untl
     step(1)
     expect(c.io.out,0)
    
  }
}

 }


}




/*




class Test_pwmTester extends ChiselFlatSpec {
  behavior of "Test_pwm"
  backends foreach {backend =>
    it should s"correctly accumulate randomly generated numbers in $backend" in {                      //class for tester
      Driver(() => new Test_pwm(8), backend)(c => new Test_pwmTests(c)) should be (true)
    }
  }
}


*/






class Test_pwm_verilatorTester extends ChiselFlatSpec{
   behavior of "Test_pwm"
   
   it should "run verilator via command line arguments" in {                           // class for verilator
    // val args = Array.empty[String]
    val args = Array("--backend-name", "verilator")
    iotesters.Driver.execute(args, () => new Test_pwm(8)) { c =>
      new Test_pwmTests(c)
    } should be (true)
  }
}









class Test_pwm_firrtlTester extends ChiselFlatSpec{
   behavior of "Test_pwm"

 it should "run firrtl via command line arguments" in {
    // val args = Array.empty[String]                                                     //classfor FIRTEL     
    val args = Array("--backend-name", "firrtl", "--fint-write-vcd")
    iotesters.Driver.execute(args, () => new Test_pwm(8)) { c =>
      new Test_pwmTests(c)
    } should be (true)
  }
}













class Test_pwm_treadleTester extends ChiselFlatSpec{
   behavior of "Test_pwm"

 it should "run treadle via command line arguments" in {
    // val args = Array.empty[String]                                                     //classfor TREADLE
    val args = Array("--backend-name", "treadle", "-tiwv")
    iotesters.Driver.execute(args, () => new Test_pwm(8)) { c =>
      new Test_pwmTests(c)
    } should be (true)
  }
}



























/*


class GCDSpec extends FlatSpec with Matchers {
  behavior of "GCDSpec"

  it should "compute gcd excellently" in {
    iotesters.Driver.execute(() => new RealGCD2, new TesterOptionsManager) { c =>
      new GCDPeekPokeTester(c)
    } should be(true)
  }

  it should "run verilator via command line arguments" in {
    // val args = Array.empty[String]
    val args = Array("--backend-name", "verilator")
    iotesters.Driver.execute(args, () => new RealGCD2) { c =>
      new GCDPeekPokeTester(c)
    } should be (true)
  }
  it should "run firrtl via command line arguments" in {
    // val args = Array.empty[String]
    val args = Array("--backend-name", "firrtl", "--fint-write-vcd")
    iotesters.Driver.execute(args, () => new RealGCD2) { c =>
      new GCDPeekPokeTester(c)
    } should be (true)
  }

  it should "run firrtl via direct options configuration" in {
    val manager = new TesterOptionsManager {
      testerOptions = testerOptions.copy(backendName = "firrtl", testerSeed = 7L)
      interpreterOptions = interpreterOptions.copy(setVerbose = false, writeVCD = true)
    }
    iotesters.Driver.execute(() => new RealGCD2, manager) { c =>
      new GCDPeekPokeTester(c)
    } should be (true)
  }

  "using verilator backend with suppress-verilator-backend" should "not create a vcd" in {
    iotesters.Driver.execute(
      Array("--backend-name", "verilator", "--generate-vcd-output", "off",
        "--target-dir", "test_run_dir/gcd_no_vcd", "--top-name", "gcd_no_vcd"),
      () => new RealGCD2
    ) {

      c => new GCDPeekPokeTester(c)
    } should be(true)

    new File("test_run_dir/gcd_no_vcd/RealGCD2.vcd").exists() should be (false)
  }

  "using verilator default behavior" should "create a vcd" in {
    iotesters.Driver.execute(
      Array("--backend-name", "verilator",
        "--target-dir", "test_run_dir/gcd_make_vcd", "--top-name", "gcd_make_vcd"),
      () => new RealGCD2
    ) {

      c => new GCDPeekPokeTester(c)
    } should be(true)

    new File("test_run_dir/gcd_make_vcd/RealGCD2.vcd").exists() should be (true)
  }
}

*/
